package br.com.fatec.easyDrive.DTO.cidade;

import br.com.fatec.easyDrive.entity.Cidade;

public record DadosDetalhamentoCidade(
		Long id,
		String nome
	) {
	public DadosDetalhamentoCidade(Cidade cidade) {
		this(
				cidade.getId(),
				cidade.getNome()
		);
	}

}
