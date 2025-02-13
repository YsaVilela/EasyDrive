package br.com.fatec.easyDrive.DTO.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;

public record DadosCliente(

    @NotBlank(message = "Número da CNH é obrigatório")
    String numeroCNH,

    @NotNull(message = "Validade da CNH é obrigatória")
    @PastOrPresent(message = "A validade da CNH não pode ser uma data futura")
    @JsonFormat(pattern = "dd/MM/yyyy")    
    LocalDate validadeCNH,

    @Valid
    DadosPessoa pessoa) {
}
