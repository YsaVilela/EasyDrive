package br.com.fatec.easyDrive.DTO.endereco;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.fatec.easyDrive.entity.Endereco;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosDetalhamenteEndereco(
		Long id,
		String cep,
		String logradouro,
		String numero,
		String complemento,
		String bairro,
		String nomeCidade,
		String nomeEstado,
		String siglaUF
	) {

	public DadosDetalhamenteEndereco(Endereco endereco) {
		this(
			endereco.getId(),
			endereco.getCep(),
			endereco.getLogradouro(),
			endereco.getNumero(),
			endereco.getComplemento(),
			endereco.getBairro(),
			endereco.getCidade().getNome(),
			endereco.getCidade().getEstado().getNome(),
			endereco.getCidade().getEstado().getSigla()
		);
	}
}
