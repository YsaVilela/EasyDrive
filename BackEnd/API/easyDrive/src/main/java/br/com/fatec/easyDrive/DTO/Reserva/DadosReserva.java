package br.com.fatec.easyDrive.DTO.Reserva;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DadosReserva(
    
    @NotBlank(message = "Data de Início é obrigatória")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$", message = "Formato de data inválido. Utilize 00/00/0000")
    String dataInicio,

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$", message = "Formato de data inválido. Utilize 00/00/0000")
    String dataFim,

    @NotNull(message = "Valor da diária é obrigatório")
    @Positive(message = "O valor da diária deve ser maior que zero")
    Double valorDiaria,

    @NotBlank(message = "Status é obrigatório")
    String status,

    @NotNull(message = "ID do Cliente é obrigatório")
    Long fkCliente,

    @NotNull(message = "ID do Veículo é obrigatório")
    Long fkVeiculo
) {
}
