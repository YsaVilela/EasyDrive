package br.com.fatec.easyDrive.DTO.estado;

import br.com.fatec.easyDrive.entity.Estado;

public record DadosDetalhamentoEstado(
	Long id,
	String nome,
	String sigla
) {
	public DadosDetalhamentoEstado(Estado estado) {
		this(
				estado.getId(),
				estado.getNome(),
				estado.getSigla()
		);
	}
}
