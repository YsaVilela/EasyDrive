package br.com.fatec.easyDrive.DTO.cliente;

import java.time.LocalDate;

import jakarta.validation.constraints.FutureOrPresent;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;

public record DadosCliente(

    String numeroCNH,

    @FutureOrPresent(message = "A validade da CNH não pode ser uma data passada")
    @JsonFormat(pattern = "dd/MM/yyyy")    
    LocalDate validadeCNH,

    Long idPessoa,
    
	DadosPessoa pessoa
	) {
}
