package br.com.fatec.easyDrive.DTO.reserva;

import br.com.fatec.easyDrive.entity.ServicoReserva;

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
