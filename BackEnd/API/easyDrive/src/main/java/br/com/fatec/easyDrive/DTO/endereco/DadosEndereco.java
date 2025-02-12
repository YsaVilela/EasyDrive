package br.com.fatec.easyDrive.DTO.endereco;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;

public record DadosEndereco(
		@Pattern(regexp = "^\\d{5}-\\d{3}$", message = "CEP inválido") 
		String cep,

		@NotBlank(message = "Logradouro  é obrigatório") 
		String logradouro,

		@NotBlank(message = "Numero é obrigatório") 
		String numero,

		String complemento,

		String bairro,

		@NotNull(message = "Cidade  é obrigatório") 
		Long fkCidade
		) {
}