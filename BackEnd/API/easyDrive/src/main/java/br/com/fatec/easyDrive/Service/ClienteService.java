package br.com.fatec.easyDrive.Service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.Cliente.DadosAtualizarCliente;
import br.com.fatec.easyDrive.DTO.Cliente.DadosCliente;
import br.com.fatec.easyDrive.DTO.Cliente.DadosDetalhamentoCliente;
import br.com.fatec.easyDrive.Entity.Cliente;
import br.com.fatec.easyDrive.Entity.Pessoa;
import br.com.fatec.easyDrive.Repository.ClienteRepository;
import jakarta.validation.Valid;

@Service
public class ClienteService {
	
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	public Optional<DadosDetalhamentoCliente> cadastrar(@Valid DadosCliente dados) {
		Pessoa pessoa = pessoaService.criarPessoa(dados.pessoa());
		Cliente cliente = new Cliente();
		cliente.setPessoa(pessoa);
		cliente.setNumeroCNH(dados.numeroCNH());
		cliente.setValidadeCNH(dados.validadeCNH());
		clienteRepository.save(cliente);
		
		return clienteRepository.findById(cliente.getId()).map(DadosDetalhamentoCliente::new);
	}
	
	public Optional<DadosDetalhamentoCliente> buscarPorId(Long id) {
		return clienteRepository.findById(id).map(DadosDetalhamentoCliente::new);
	}
	
	public Page<DadosDetalhamentoCliente> listarTodos(Pageable paginacao) {
		return clienteRepository.findAll(paginacao).map(DadosDetalhamentoCliente::new);
	}
	
	public Optional<DadosDetalhamentoCliente> atualizar(@Valid DadosAtualizarCliente dados) {	
		Cliente cliente = clienteRepository.getReferenceById(dados.id());

		Pessoa pessoa = pessoaService.atualizar(dados.pessoa(), cliente.getPessoa().getId());
		cliente.setPessoa(pessoa);
		cliente.setNumeroCNH(dados.numeroCNH());
		cliente.setValidadeCNH(dados.validadeCNH());
		clienteRepository.save(cliente);
	
		return clienteRepository.findById(cliente.getId()).map(DadosDetalhamentoCliente::new);
	}
	
	
	public void deletar(Long id) {
		Cliente cliente = clienteRepository.getReferenceById(id);
		clienteRepository.deleteById(id);
		pessoaService.deletarPessoa(cliente.getPessoa().getId());
	}
}
