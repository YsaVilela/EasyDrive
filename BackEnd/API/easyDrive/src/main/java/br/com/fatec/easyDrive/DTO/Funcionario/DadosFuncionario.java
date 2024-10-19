package br.com.fatec.easyDrive.DTO.Funcionario;

import br.com.fatec.easyDrive.DTO.Pessoa.DadosPessoa;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;

public record DadosFuncionario(
	@NotBlank(message = "Cargo é obrigatório") 
	String cargo,

	@Valid  
	DadosPessoa pessoa) {

}
