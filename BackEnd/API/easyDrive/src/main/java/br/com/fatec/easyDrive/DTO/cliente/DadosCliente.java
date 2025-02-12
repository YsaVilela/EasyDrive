package br.com.fatec.easyDrive.DTO.cliente;

import java.util.Date;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DadosCliente(

    @NotBlank(message = "Número da CNH é obrigatório")
    String numeroCNH,

    Date validadeCNH,

    @Valid
    DadosPessoa pessoa) {
}
