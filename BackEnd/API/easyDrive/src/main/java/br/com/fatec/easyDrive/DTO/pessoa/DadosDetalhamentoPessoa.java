package br.com.fatec.easyDrive.DTO.pessoa;

import java.time.LocalDate;

import br.com.fatec.easyDrive.entity.Pessoa;

public record DadosDetalhamentoPessoa(
			String nome,
			String cpf,
			LocalDate dataDeNascimento,
			String telefone,
			String email,
			LocalDate dataCadastro,
			String cep,
			String logradouro,
			String numero,
			String complemento,
			String bairro,
			String nomeCidade,
			String nomeEstado,
			String siglaUF
		) {
	public DadosDetalhamentoPessoa (Pessoa pessoa) {
		this(
			pessoa.getNome(),
			pessoa.getCpf(),
			pessoa.getDataDeNascimento(),
			pessoa.getTelefone(),
			pessoa.getEmail(),
			pessoa.getDataCadastro(),
			pessoa.getEndereco().getCep(),
			pessoa.getEndereco().getLogradouro(),
			pessoa.getEndereco().getNumero(),
			pessoa.getEndereco().getComplemento(),
			pessoa.getEndereco().getBairro(),
			pessoa.getEndereco().getCidade().getNome(),
			pessoa.getEndereco().getCidade().getEstado().getNome(),
			pessoa.getEndereco().getCidade().getEstado().getSigla()
		);
	}

}
