package br.com.fatec.easyDrive.DTO.reserva;

import java.time.LocalDateTime;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatec.easyDrive.enumerator.ServicoEnum;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;

public record DadosReserva(
    
	@NotNull(message = "Data inicio da reserva é obrigatório")
    @FutureOrPresent(message = "A data de inicio da reserva não pode ser passada")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")    
	LocalDateTime dataInicio,

    @NotNull(message = "Data prevista para o fim da reserva é obrigatório")
	@FutureOrPresent(message = "A data final da reserva não pode ser passada")
    @JsonFormat(pattern = "dd/MM/yyyy HH:mm")    
	LocalDateTime dataPrevistaFim,

    @NotNull(message = "ID do Cliente é obrigatório")
    Long fkCliente,

    @NotNull(message = "ID do Veículo é obrigatório")
    Long fkVeiculo,
    
    List<ServicoEnum> servicos
) {
}
