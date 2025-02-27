package br.com.fatec.easyDrive.DTO.reserva;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.fatec.easyDrive.entity.ServicoReserva;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosDatalhamentoServicoReserva(
		String nomeServico,
		Double valor
		) {
	public DadosDatalhamentoServicoReserva(ServicoReserva servicoReserva) {
		this(
			servicoReserva.getNome().getDescricao(),
			servicoReserva.getValor()
		);
	}
}
