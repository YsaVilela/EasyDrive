package br.com.fatec.easyDrive.DTO.Pagamento;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;

public record DadosPagamento(

    @NotNull(message = "Valor total é obrigatório")
    @Positive(message = "O valor total deve ser maior que zero")
    Double valorTotal,

    @NotBlank(message = "Forma de pagamento é obrigatória")
    String formaPagamento,

    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$", message = "Formato de data inválido. Utilize 00/00/0000")
    String dataPagamento,

    @NotBlank(message = "Status é obrigatório")
    String status,

    @NotNull(message = "ID da Reserva é obrigatório")
    Long fkReserva
) {
}
