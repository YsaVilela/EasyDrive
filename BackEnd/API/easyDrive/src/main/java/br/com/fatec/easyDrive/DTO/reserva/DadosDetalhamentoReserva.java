package br.com.fatec.easyDrive.DTO.reserva;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.fatec.easyDrive.DTO.cliente.DadosClienteReserva;
import br.com.fatec.easyDrive.DTO.veiculo.DadosDetalhamentoVeiculo;
import br.com.fatec.easyDrive.entity.Reserva;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosDetalhamentoReserva(
		Long id,
		LocalDateTime dataInicio,
		LocalDateTime dataRetirada,
		LocalDateTime dataFim,
		LocalDateTime dataDevolucao,
	    Double orcamento,
	    Double orcamentoFinal,
	    DadosClienteReserva cliente,
	    DadosDetalhamentoVeiculo veiculo,
        List<DadosDatalhamentoServicoReserva> servicos
	) {
	
	public DadosDetalhamentoReserva(Reserva reserva, List<DadosDatalhamentoServicoReserva> servicos) {
		this(
			reserva.getId(),
			reserva.getDataInicio(),
			reserva.getDataRetirada(),
			reserva.getDataFim(),
			reserva.getDataDevolucao(),
			reserva.getOrcamento(),
			reserva.getOrcamentoFinal(),
			new DadosClienteReserva(reserva.getCliente()),
			new DadosDetalhamentoVeiculo(reserva.getVeiculo()),
			servicos
		);
	}
}
