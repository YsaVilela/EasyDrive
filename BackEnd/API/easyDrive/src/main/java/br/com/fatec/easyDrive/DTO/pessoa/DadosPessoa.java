package br.com.fatec.easyDrive.DTO.pessoa;

import java.time.LocalDate;

import com.fasterxml.jackson.annotation.JsonFormat;

import br.com.fatec.easyDrive.DTO.endereco.DadosEndereco;
import br.com.fatec.easyDrive.exception.anotations.CPF;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Pattern;

public record DadosPessoa(
		@NotBlank (message = "Nome é obrigatório") 
		String nome,
		
		@Pattern(regexp = "^\\d{3}\\.\\d{3}\\.\\d{3}-\\d{2}$", message = "Formatação inválida, utilize xxx.xxx.xxx-xx")
		@CPF
		String cpf,
		
	    @PastOrPresent(message = "A data de nascimento não pode ser uma data futura")
	    @JsonFormat(pattern = "dd/MM/yyyy")
	    LocalDate dataDeNascimento,

		@NotBlank(message = "Telefone é obrigatório") 
		@Pattern(regexp = "^\\(\\d{2}\\)\\d{5}-\\d{4}$", message = "Telefone inválido") 
		String telefone,

		@NotBlank(message = "Email é obrigatório") 
		@Pattern(regexp = "^[a-zA-Z0-9_+&*-]+(?:\\.[a-zA-Z0-9_+&*-]+)*@(?:[a-zA-Z0-9-]+\\.)+[a-zA-Z]{2,7}$", message = "Email inválido") 
		String email,

		@Valid  
		DadosEndereco endereco) {

}
