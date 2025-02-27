package br.com.fatec.easyDrive.service;

import java.time.LocalDate;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.pessoa.DadosDetalhamentoPessoa;
import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.entity.Cidade;
import br.com.fatec.easyDrive.entity.Endereco;
import br.com.fatec.easyDrive.entity.Pessoa;
import br.com.fatec.easyDrive.exception.InvalidDataException;
import br.com.fatec.easyDrive.exception.NotFoundException;
import br.com.fatec.easyDrive.repository.CidadeRepository;
import br.com.fatec.easyDrive.repository.EnderecoRepository;
import br.com.fatec.easyDrive.repository.PessoaRepository;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private CidadeRepository cidadeRepository;
	
	private Cidade buscarCidade(Long idCidade) {		
		return cidadeRepository.findById(idCidade).orElseThrow(() -> 
			new NotFoundException("Cidade com id " + idCidade + " não encontrado")
		);
	}
	
	public DadosDetalhamentoPessoa cadastrar(@Valid DadosPessoa dados) {
		validarCadastroPessoaJaCadastrada(dados);
		
		Endereco endereco = new Endereco();
		endereco.setCep(dados.endereco().cep());
		endereco.setLogradouro(dados.endereco().logradouro());
		endereco.setNumero(dados.endereco().numero());
		endereco.setComplemento(dados.endereco().complemento());
		endereco.setBairro(dados.endereco().bairro());
		endereco.setCidade(buscarCidade(dados.endereco().fkCidade()));

		enderecoRepository.save(endereco);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(dados.nome());
		pessoa.setCpf(dados.cpf());
		pessoa.setDataDeNascimento(dados.dataDeNascimento());
		pessoa.setTelefone(dados.telefone());
		pessoa.setEmail(dados.email());
		pessoa.setDataCadastro(LocalDate.now());
		pessoa.setEndereco(endereco);
		pessoaRepository.save(pessoa);
		
		return new DadosDetalhamentoPessoa(pessoa);
	}
	
	public Pessoa atualizar(@Valid DadosPessoa dados, Pessoa pessoa) {
		validarAtualizacaoPessoaJaCadastrada(dados, pessoa.getId());
		
		Endereco endereco = pessoa.getEndereco();
				
		endereco.setCep(dados.endereco().cep());
		endereco.setLogradouro(dados.endereco().logradouro());
		endereco.setNumero(dados.endereco().numero());
		endereco.setComplemento(dados.endereco().complemento());
		endereco.setBairro(dados.endereco().bairro());
		endereco.setCidade(buscarCidade(dados.endereco().fkCidade()));

		enderecoRepository.save(endereco);		

		pessoa.setNome(dados.nome());
		pessoa.setCpf(dados.cpf());
		pessoa.setDataDeNascimento(dados.dataDeNascimento());
		pessoa.setTelefone(dados.telefone());
		pessoa.setEmail(dados.email());
		pessoa.setEndereco(enderecoRepository.getReferenceById(endereco.getId()));
		
		pessoaRepository.save(pessoa);
		
		return pessoaRepository.getReferenceById(pessoa.getId());
	}
	
	public DadosDetalhamentoPessoa buscarPorCPF(String cpf) {
		Pessoa pessoa = pessoaRepository.findByCpf(cpf).orElseThrow(() -> 
			new NotFoundException("Pessoa com cpf " + cpf + " não encontrado")
		);
		
		return new DadosDetalhamentoPessoa(pessoa);
	} 
	
	public Pessoa buscarPessoaPorId(Long idPessoa) {
		return pessoaRepository.findById(idPessoa).orElseThrow(() -> 
			new NotFoundException("Pessoa com id " + idPessoa + " não encontrado")
		);
	}
	
	public void validarCadastroPessoaJaCadastrada(DadosPessoa dados) {
		pessoaRepository.findByCpf(dados.cpf())
		    .ifPresent(cliente -> {
		        throw new InvalidDataException("CPF " + dados.cpf()  + " já cadastrado");
		    });
		
		pessoaRepository.findByEmail(dados.email())
		    .ifPresent(cliente -> {
		        throw new InvalidDataException("Email " + dados.email()  + " já cadastrado");
		    });
	}
	
	public void validarAtualizacaoPessoaJaCadastrada(DadosPessoa dados, Long idPessoa) {
		pessoaRepository.findByCpf(dados.cpf())
		    .filter(c -> !c.getId().equals(idPessoa))
		    .ifPresent(c -> { throw new InvalidDataException("CPF " + dados.cpf()  + " já cadastrado");});
		
		pessoaRepository.findByEmail(dados.email())
		    .filter(c -> !c.getId().equals(idPessoa))
		    .ifPresent(c -> { throw new InvalidDataException("Email " + dados.email()  + " já cadastrado");});
		
	}
	
}
