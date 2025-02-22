package br.com.fatec.easyDrive.DTO.pagamento;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import br.com.fatec.easyDrive.enumerator.FormaPagamentoEnum;

public record DadosPagamento(

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    Double valor,

    @NotNull(message = "Forma de pagamento é obrigatório")
    FormaPagamentoEnum formaPagamento,

    @NotNull(message = "ID da Reserva é obrigatório")
    Long idReserva
) {
}
