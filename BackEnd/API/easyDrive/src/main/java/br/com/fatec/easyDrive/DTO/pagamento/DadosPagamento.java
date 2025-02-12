package br.com.fatec.easyDrive.DTO.pagamento;

import br.com.fatec.easyDrive.enumerator.FormaPagamentoEnum;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DadosPagamento(

    @NotNull(message = "Valor é obrigatório")
    @Positive(message = "O valor deve ser maior que zero")
    Double valor,

    @NotBlank(message = "Forma de pagamento é obrigatório")
    FormaPagamentoEnum formaPagamento,

    @NotNull(message = "ID da Reserva é obrigatório")
    Long fkReserva
) {
}
