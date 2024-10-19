package br.com.fatec.easyDrive.DTO.Endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
		@Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido") 
		String cep,

		@NotBlank(message = "Logradouro  é obrigatório") 
		String logradouro,

		Long numero,

		String complemento,

		String bairro,

		@NotBlank(message = "Cidade  é obrigatório") 
		String cidade,

		@NotBlank(message = "Estado  é obrigatório") 
		String estado,
		
		@NotBlank(message = "Pais  é obrigatório") 
		String pais) {
}