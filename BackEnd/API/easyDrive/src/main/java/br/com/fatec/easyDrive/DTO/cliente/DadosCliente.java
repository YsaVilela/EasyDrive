package br.com.fatec.easyDrive.DTO.cliente;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;

public record DadosCliente(

    @NotBlank(message = "Número da CNH é obrigatório")
    String numeroCNH,

    @NotNull(message = "Validade da CNH é obrigatória")
    @FutureOrPresent(message = "A validade da CNH não pode ser uma data passada")
    @JsonFormat(pattern = "dd/MM/yyyy")    
    LocalDate validadeCNH,

    Long idPessoa,
    
	DadosPessoa pessoa
	) {
}
