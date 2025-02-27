package br.com.fatec.easyDrive.DTO.pessoa;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.fatec.easyDrive.DTO.endereco.DadosDetalhamenteEndereco;
import br.com.fatec.easyDrive.entity.Pessoa;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosDetalhamentoPessoa(
			Long id,
			String nome,
			String cpf,
			LocalDate dataDeNascimento,
			String telefone,
			String email,
			LocalDate dataCadastro,
			DadosDetalhamenteEndereco endereco

		) {
	public DadosDetalhamentoPessoa (Pessoa pessoa) {
		this(
			pessoa.getId(),
			pessoa.getNome(),
			pessoa.getCpf(),
			pessoa.getDataDeNascimento(),
			pessoa.getTelefone(),
			pessoa.getEmail(),
			pessoa.getDataCadastro(),
			new DadosDetalhamenteEndereco(pessoa.getEndereco())
		);
	}

}
