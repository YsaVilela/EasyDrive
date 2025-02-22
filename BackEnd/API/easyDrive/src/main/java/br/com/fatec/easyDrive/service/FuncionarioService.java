package br.com.fatec.easyDrive.service;

import java.time.LocalDate;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.funcionario.DadosDetalhamentoFuncionario;
import br.com.fatec.easyDrive.DTO.funcionario.DadosFuncionario;
import br.com.fatec.easyDrive.entity.Funcionario;
import br.com.fatec.easyDrive.entity.Pessoa;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
import br.com.fatec.easyDrive.exception.NotFoundException;
import br.com.fatec.easyDrive.repository.FuncionarioRepository;

@Service
public class FuncionarioService {
	@Autowired
	private PessoaService pessoaService;
	
	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	public DadosDetalhamentoFuncionario cadastrar(@Valid DadosFuncionario dados) {	
		Pessoa pessoa = pessoaService.buscarPessoaPorId(dados.idPessoa());

		Funcionario funcionario = new Funcionario();
		funcionario.setPessoa(pessoa);
		funcionario.setCargo(dados.cargo());
		funcionario.setDataCadastro(LocalDate.now());
		funcionario.setStatus(StatusEnum.ATIVO);
		funcionarioRepository.save(funcionario);
		
		return new DadosDetalhamentoFuncionario(funcionario);
	}
	
	public DadosDetalhamentoFuncionario buscarPorId(Long id) {
		Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Funcionário com id " + id + " não encontrado")
		);

		return new DadosDetalhamentoFuncionario(funcionario);
	}
	
	public Page<DadosDetalhamentoFuncionario> listarTodos(Pageable paginacao) {
		return funcionarioRepository.findAll(paginacao).map(DadosDetalhamentoFuncionario::new);
	}

	public DadosDetalhamentoFuncionario atualizar(@Valid DadosFuncionario dados, Long idFunciocario) {	
		Funcionario funcionario = funcionarioRepository.findById(idFunciocario).orElseThrow(() -> 
			new NotFoundException("Funcionário com id " + idFunciocario + " não encontrado")
		);

		Pessoa pessoa = pessoaService.atualizar(dados.pessoa(), funcionario.getPessoa());
		funcionario.setPessoa(pessoa);
		funcionario.setCargo(dados.cargo());
		funcionarioRepository.save(funcionario);
	
		return new DadosDetalhamentoFuncionario(funcionario);
	}
	
	public DadosDetalhamentoFuncionario suspender(Long id) {
		Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Funcionário com id " + id + " não encontrado")
		);
		
		funcionario.setStatus(StatusEnum.SUSPENSO);
		funcionarioRepository.save(funcionario);

		return new DadosDetalhamentoFuncionario(funcionario);
	}
	
	public DadosDetalhamentoFuncionario ativar(Long id) {
		Funcionario funcionario = funcionarioRepository.findById(id).orElseThrow(() -> 
			new NotFoundException("Funcionário com id " + id + " não encontrado")
		);
		
		funcionario.setStatus(StatusEnum.ATIVO);
		funcionarioRepository.save(funcionario);

		return new DadosDetalhamentoFuncionario(funcionario);
	}
}
