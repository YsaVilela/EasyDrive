package br.com.fatec.easyDrive.DTO.reserva;

import java.time.LocalDate;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosReserva(
    
	@NotNull(message = "Data inicio da reserva é obrigatório")
    LocalDate dataInicio,

    @NotNull(message = "Data prevista para o fim da reserva é obrigatório")
    LocalDate dataPrevistaFim,

    @NotNull(message = "O valor do orçamento é obrigatório")
    @Positive(message = "O valor do orçamento deve ser maior que zero")
    Double orcamento,

    @NotNull(message = "ID do Cliente é obrigatório")
    Long fkCliente,

    @NotNull(message = "ID do Veículo é obrigatório")
    Long fkVeiculo
) {
}
