package br.com.fatec.easyDrive.DTO.reserva;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;

import br.com.fatec.easyDrive.DTO.cliente.DadosClienteReserva;
import br.com.fatec.easyDrive.DTO.veiculo.DadosDetalhamentoVeiculo;
import br.com.fatec.easyDrive.entity.Reserva;
import br.com.fatec.easyDrive.enumerator.ServicoEnum;

@JsonInclude(JsonInclude.Include.NON_NULL)
public record DadosDetalhamentoReserva(
		Long id,
		LocalDateTime dataInicio,
		LocalDateTime dataPrevistaFim,
		LocalDateTime dataFim,
	    Double orcamento,
	    Double orcamentoFinal,
	    DadosClienteReserva cliente,
	    DadosDetalhamentoVeiculo veiculo,
        List<String> servicos
	) {
	
	public DadosDetalhamentoReserva(Reserva reserva, List<ServicoEnum> servicos) {
		this(
			reserva.getId(),
			reserva.getDataInicio(),
			reserva.getDataPrevistaFim(),
			reserva.getDataFim(),
			reserva.getOrcamento(),
			reserva.getOrcamentoFinal(),
			new DadosClienteReserva(reserva.getCliente()),
			new DadosDetalhamentoVeiculo(reserva.getVeiculo()),
			servicos.stream()
			    .map(ServicoEnum::getDescricao)
			    .collect(Collectors.toList())
		);
	}
}
