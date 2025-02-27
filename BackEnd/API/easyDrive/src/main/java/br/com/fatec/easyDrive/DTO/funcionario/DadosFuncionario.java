package br.com.fatec.easyDrive.DTO.funcionario;

import jakarta.validation.constraints.NotNull;

import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.enumerator.CargoEnum;

public record DadosFuncionario(
	@NotNull(message = "Cargo é obrigatório") 
	CargoEnum cargo,
	
	Long idPessoa,

	DadosPessoa pessoa) {

}
