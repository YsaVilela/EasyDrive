package br.com.fatec.easyDrive.DTO.reserva;

import java.time.LocalDateTime;

import br.com.fatec.easyDrive.entity.Reserva;

public record DadosResumoReserva(
		Long id,
		LocalDateTime dataInicio,
		LocalDateTime dataFim,
		String status,
		String placaVeiculo,
		String modeloVeiculo,
		String nomeCliente
	) {
	public DadosResumoReserva(Reserva reserva) {
		this(
			reserva.getId(),
			reserva.getDataInicio(),
			reserva.getDataFim(),
			reserva.getStatus().getDescricao(),
			reserva.getVeiculo().getPlaca(),
			reserva.getVeiculo().getModelo(),
			reserva.getCliente().getPessoa().getNome()
		);
	}
}
