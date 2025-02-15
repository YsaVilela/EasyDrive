package br.com.fatec.easyDrive.service;

import java.time.LocalDate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.entity.Cidade;
import br.com.fatec.easyDrive.entity.Endereco;
import br.com.fatec.easyDrive.entity.Pessoa;
import br.com.fatec.easyDrive.exception.NotFoundException;
import br.com.fatec.easyDrive.repository.CidadeRepository;
import br.com.fatec.easyDrive.repository.EnderecoRepository;
import br.com.fatec.easyDrive.repository.PessoaRepository;
import jakarta.validation.Valid;

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
	
	public Pessoa criarPessoa(@Valid DadosPessoa dados) {
		
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
		
		return pessoaRepository.getReferenceById(pessoa.getId());
	}
	
	public Pessoa atualizar(@Valid DadosPessoa dados, long idPessoa) {
		Pessoa pessoa = pessoaRepository.findById(idPessoa).orElseThrow(() -> 
			new NotFoundException("Pessoa com id " + idPessoa + " não encontrado")
		);
		
		Long idEndereco = pessoa.getEndereco().getId();
		Endereco endereco = enderecoRepository.findById(idEndereco).orElseThrow(() -> 
			new NotFoundException("Endereco com id " + idEndereco + " não encontrado")
		);
				
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
}
