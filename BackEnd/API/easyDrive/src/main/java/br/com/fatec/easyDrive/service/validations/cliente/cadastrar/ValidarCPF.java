package br.com.fatec.easyDrive.service.validations.cliente.cadastrar;

import org.springframework.stereotype.Component;

import br.com.fatec.easyDrive.DTO.cliente.DadosCliente;
import br.com.fatec.easyDrive.exception.InvalidDataException;

@Component
public class ValidarCPF implements ValidadorCadastroCliente {

	@Override
	public void validarCadastro(DadosCliente dados) {
	    String cpf = dados.pessoa().cpf().replaceAll("\\D", "");

	    if (cpf.length() != 11 || cpf.matches("(\\d)\\1{10}")) {
	        throw new InvalidDataException("CPF Inválido");
	    }

	    if (!validarDigitoVerificador(cpf, 9) || !validarDigitoVerificador(cpf, 10)) {
	        throw new InvalidDataException("CPF Inválido");
	    }
	}

	private boolean validarDigitoVerificador(String cpf, int posicao) {
	    int soma = 0;
	    int pesoInicial = posicao + 1; 

	    for (int i = 0; i < posicao; i++) {
	        soma += (cpf.charAt(i) - '0') * (pesoInicial - i);
	    }

	    int digitoCalculado = 11 - (soma % 11);
	    if (digitoCalculado > 9) digitoCalculado = 0;

	    return digitoCalculado == (cpf.charAt(posicao) - '0');
	}

}
