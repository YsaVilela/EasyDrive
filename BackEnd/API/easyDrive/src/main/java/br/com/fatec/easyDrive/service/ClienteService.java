package br.com.fatec.easyDrive.service;

import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.cliente.DadosCliente;
import br.com.fatec.easyDrive.DTO.cliente.DadosDetalhamentoCliente;
import br.com.fatec.easyDrive.entity.Cliente;
import br.com.fatec.easyDrive.entity.Pessoa;
import br.com.fatec.easyDrive.entity.Reserva;
import br.com.fatec.easyDrive.enumerator.PlanoAssinaturaEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
import br.com.fatec.easyDrive.exception.InvalidDataException;
import br.com.fatec.easyDrive.exception.NotFoundException;
import br.com.fatec.easyDrive.repository.ClienteRepository;
import br.com.fatec.easyDrive.repository.ReservaRepository;

@Service
public class ClienteService {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	public DadosDetalhamentoCliente cadastrar(DadosCliente dados) {
		
		verificarClienteJaCadastrado(dados);
		
		Pessoa pessoa = pessoaService.buscarPessoaPorId(dados.idPessoa());
		
		Cliente cliente = new Cliente();
		cliente.setPessoa(pessoa);
		cliente.setNumeroCNH(dados.numeroCNH());
		cliente.setValidadeCNH(dados.validadeCNH());
		cliente.setPontuacao(0L);
		cliente.setPlanoAssinatura(PlanoAssinaturaEnum.ECONOMICO);
		cliente.setStatus(StatusEnum.ATIVO);
		cliente.setDataCadastro(LocalDate.now());
		clienteRepository.save(cliente);
		
		return new DadosDetalhamentoCliente(cliente);
	}
	
	public DadosDetalhamentoCliente buscarPorId(Long id) {
		Cliente cliente = buscarClientePorId(id);

		return new DadosDetalhamentoCliente(cliente);
	}
	
	public DadosDetalhamentoCliente buscarPorCpf(String cpf) {
		Cliente cliente = clienteRepository.findByCpf(cpf).orElseThrow(() -> 
			new NotFoundException("Cliente com cpf " + cpf + " não encontrado")
		);

		return new DadosDetalhamentoCliente(cliente);
	}
	
	public Page<DadosDetalhamentoCliente> listarTodos(Pageable paginacao) {
		return clienteRepository.findAll(paginacao).map(DadosDetalhamentoCliente::new);
	}

	public DadosDetalhamentoCliente atualizar(DadosCliente dados, Long idCliente) {	
		verificarClienteJaCadastradoAtualizacao(dados, idCliente);
		
		Cliente cliente = buscarClientePorId(idCliente);

		Pessoa pessoa = pessoaService.atualizar(dados.pessoa(), cliente.getPessoa());
		cliente.setPessoa(pessoa);
		cliente.setNumeroCNH(dados.numeroCNH());
		cliente.setValidadeCNH(dados.validadeCNH());
		clienteRepository.save(cliente);
	
		return new DadosDetalhamentoCliente(cliente);
	}
	
	public DadosDetalhamentoCliente suspender(Long id) {
		Cliente cliente = buscarClientePorId(id);
		
		cliente.setStatus(StatusEnum.SUSPENSO);
		clienteRepository.save(cliente);

		return new DadosDetalhamentoCliente(cliente);
	}
	
	public DadosDetalhamentoCliente ativar(Long id) {
		Cliente cliente = buscarClientePorId(id);
		
		cliente.setStatus(StatusEnum.ATIVO);
		clienteRepository.save(cliente);

		return new DadosDetalhamentoCliente(cliente);
	}
	
	public void deletar(Long id) {
		Cliente cliente = buscarClientePorId(id);
		
		List<Reserva> reservas = reservaRepository.findByClienteId(id);
		if(!reservas.isEmpty()) {
			throw new InvalidDataException ("Não é possivel excluir cliente que já realizou reserva");		
		}
		clienteRepository.delete(cliente);
	}
	
	public Cliente buscarClientePorId(Long id) {
		return clienteRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + id + " não encontrado")
		);
	}
	
	public void verificarClienteJaCadastrado(DadosCliente dados) {
		clienteRepository.findByIdPessoa(dados.idPessoa())
		    .ifPresent(cliente -> {
		        throw new InvalidDataException("Cliente já possui cadastro");
		    });
	    
		clienteRepository.findByNumeroCNH(dados.numeroCNH())
			.ifPresent(cliente -> {
				throw new InvalidDataException("AA Cliente já cadastrado com esse número de Carteira Nacional de Habilitação(CNH)");
			});
	}
	
	public void verificarClienteJaCadastradoAtualizacao(DadosCliente dados, Long idCliente) {
		clienteRepository.findByNumeroCNH(dados.numeroCNH())
		    .filter(c -> !c.getId().equals(idCliente))
		    .ifPresent(c -> { throw new InvalidDataException("Cliente já cadastrado com esse número de Carteira Nacional de Habilitação(CNH)"); });
	
	}
	
	public void atualizarPlano(Long pontuacao, Long idCliente) {
		Cliente cliente = buscarClientePorId(idCliente);
		
		cliente.setPontuacao(cliente.getPontuacao() + pontuacao);
		cliente.setPlanoAssinatura(PlanoAssinaturaEnum.getPlanoPorPontuacao(cliente.getPontuacao()));
		
		clienteRepository.save(cliente);
	}
	

}
