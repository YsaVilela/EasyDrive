package br.com.fatec.easyDrive.DTO.funcionario;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.enumerator.CargoEnum;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DadosFuncionario(
	@NotBlank(message = "Cargo é obrigatório") 
	CargoEnum cargo,

	@Valid  
	DadosPessoa pessoa) {

}
