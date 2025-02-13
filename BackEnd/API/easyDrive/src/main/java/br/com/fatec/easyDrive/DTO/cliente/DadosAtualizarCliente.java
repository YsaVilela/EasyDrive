package br.com.fatec.easyDrive.DTO.cliente;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosAtualizarCliente(
		@NotNull (message = "Campo Id é obrigatótio")
		Long id,
		
	    @NotBlank(message = "Número da CNH é obrigatório")
	    @Pattern(regexp = "^[0-9]{9}$", message = "Numero da CNH deve conter 9 digitos")
	    String numeroCNH,

	    @JsonFormat(pattern = "dd/MM/yyyy")
	    LocalDate validadeCNH,

	    @Valid
	    DadosPessoa pessoa) {

}
