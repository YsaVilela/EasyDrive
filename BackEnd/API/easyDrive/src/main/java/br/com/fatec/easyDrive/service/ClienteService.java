package br.com.fatec.easyDrive.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.cliente.DadosAtualizarCliente;
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
import br.com.fatec.easyDrive.service.validations.cliente.cadastrar.ValidadorCadastroCliente;
import jakarta.validation.Valid;

@Service
public class ClienteService {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	@Autowired
	private List<ValidadorCadastroCliente> validadorCadastroCliente;
	
	public DadosDetalhamentoCliente cadastrar(@Valid DadosCliente dados) {
		validadorCadastroCliente.forEach(v -> v.validarCadastro(dados));
		
		Pessoa pessoa = pessoaService.criarPessoa(dados.pessoa());
		Cliente cliente = new Cliente();
		cliente.setPessoa(pessoa);
		cliente.setNumeroCNH(dados.numeroCNH());
		cliente.setValidadeCNH(dados.validadeCNH());
		cliente.setPontuacao(0L);
		cliente.setPlanoAssinatura(PlanoAssinaturaEnum.ECONOMICO);
		cliente.setStatus(StatusEnum.ATIVO);
		clienteRepository.save(cliente);
		
		return new DadosDetalhamentoCliente(cliente);
	}
	
	public DadosDetalhamentoCliente buscarPorId(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + id + " não encontrado")
		);

		return new DadosDetalhamentoCliente(cliente);
	}
	
	public Page<DadosDetalhamentoCliente> listarTodos(Pageable paginacao) {
		return clienteRepository.findAll(paginacao).map(DadosDetalhamentoCliente::new);
	}

	public Optional<DadosDetalhamentoCliente> atualizar(@Valid DadosAtualizarCliente dados) {	
		Cliente cliente = clienteRepository.findById(dados.id()).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + dados.id() + " não encontrado")
		);

		Pessoa pessoa = pessoaService.atualizar(dados.pessoa(), cliente.getPessoa().getId());
		cliente.setPessoa(pessoa);
		cliente.setNumeroCNH(dados.numeroCNH());
		cliente.setValidadeCNH(dados.validadeCNH());
		clienteRepository.save(cliente);
	
		return clienteRepository.findById(cliente.getId()).map(DadosDetalhamentoCliente::new);
	}
	
	public DadosDetalhamentoCliente suspender(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + id + " não encontrado")
		);
		
		cliente.setStatus(StatusEnum.SUSPENSO);
		clienteRepository.save(cliente);

		return new DadosDetalhamentoCliente(cliente);
	}
	
	public DadosDetalhamentoCliente ativar(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + id + " não encontrado")
		);
		
		cliente.setStatus(StatusEnum.ATIVO);
		clienteRepository.save(cliente);

		return new DadosDetalhamentoCliente(cliente);
	}
	
	public void deletar(Long id) {
		Cliente cliente = clienteRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Cliente com id " + id + " não encontrado")
		);
		
		List<Reserva> reservas = reservaRepository.getByClienteId(id);
		if(!reservas.isEmpty()) {
			throw new InvalidDataException ("Não é possivel excluir cliente que já realizou reserva");		
		}
		clienteRepository.delete(cliente);
	}
}
