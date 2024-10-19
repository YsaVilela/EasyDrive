package br.com.fatec.easyDrive.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.Pessoa.DadosPessoa;
import br.com.fatec.easyDrive.Entity.Endereco;
import br.com.fatec.easyDrive.Entity.Pessoa;
import br.com.fatec.easyDrive.Repository.EnderecoRepository;
import br.com.fatec.easyDrive.Repository.PessoaRepository;
import jakarta.validation.Valid;

@Service
public class PessoaService {
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	public Pessoa criarPessoa(@Valid DadosPessoa dados) {
		Endereco endereco = new Endereco();
		endereco.setCep(dados.endereco().cep());
		endereco.setLogradouro(dados.endereco().logradouro());
		endereco.setNumero(dados.endereco().numero());
		endereco.setComplemento(dados.endereco().complemento());
		endereco.setBairro(dados.endereco().bairro());
		endereco.setCidade(dados.endereco().cidade());
		endereco.setEstado(dados.endereco().estado());
		endereco.setPais(dados.endereco().pais());

		enderecoRepository.save(endereco);
		
		LocalDate dataAtual = LocalDate.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
        String dataAtualFormatada = dataAtual.format(formatter);
		
		Pessoa pessoa = new Pessoa();
		pessoa.setNome(dados.nome());
		pessoa.setCpf(dados.cpf());
		pessoa.setDataDeNascimento(dados.dataDeNascimento());
		pessoa.setTelefone(dados.telefone());
		pessoa.setEmail(dados.email());
		pessoa.setDataCadastro(dataAtualFormatada);
		pessoa.setEndereco(enderecoRepository.getReferenceById(endereco.getId()));
		pessoa.setStatus("Ativo");
		pessoaRepository.save(pessoa);
		
		return pessoaRepository.getReferenceById(pessoa.getId());
	}
	
	public Pessoa atualizar(@Valid DadosPessoa dados, long id) {
		Pessoa pessoa = pessoaRepository.getReferenceById(id);
		
		Endereco endereco = enderecoRepository.getReferenceById(pessoa.getEndereco().getId());
		endereco.setCep(dados.endereco().cep());
		endereco.setLogradouro(dados.endereco().logradouro());
		endereco.setNumero(dados.endereco().numero());
		endereco.setComplemento(dados.endereco().complemento());
		endereco.setBairro(dados.endereco().bairro());
		endereco.setCidade(dados.endereco().cidade());
		endereco.setEstado(dados.endereco().estado());
		endereco.setPais(dados.endereco().pais());
		enderecoRepository.save(endereco);		

		pessoa.setNome(dados.nome());
		pessoa.setCpf(dados.cpf());
		pessoa.setDataDeNascimento(dados.dataDeNascimento());
		pessoa.setTelefone(dados.telefone());
		pessoa.setEmail(dados.email());
		pessoa.setEndereco(enderecoRepository.getReferenceById(endereco.getId()));
		pessoa.setStatus("Ativo");
		
		pessoaRepository.save(pessoa);
		
		return pessoaRepository.getReferenceById(pessoa.getId());
	}
	
	public void atualizarStatus(Long id) {
		Pessoa pessoa = pessoaRepository.getReferenceById(id);
		pessoa.setStatus("Cancelada");
	}
	
	public void deletarPessoa(Long id) {
		Pessoa pessoa = pessoaRepository.getReferenceById(id);
		pessoaRepository.deleteById(id);
		enderecoRepository.deleteById(pessoa.getEndereco().getId()); 
	}
}
