package br.com.fatec.easyDrive.DTO.Cliente;

import br.com.fatec.easyDrive.DTO.Pessoa.DadosPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosCliente(

    @NotBlank(message = "Número da CNH é obrigatório")
    String numeroCNH,

    @NotBlank(message = "Validade da CNH é obrigatória")
    @Pattern(regexp = "^(0[1-9]|[12][0-9]|3[01])/(0[1-9]|1[0-2])/(\\d{4})$", message = "Formato de data inválido. Utilize 00/00/0000")
    String validadeCNH,

    @Valid
    DadosPessoa pessoa) {
}
