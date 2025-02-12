package br.com.fatec.easyDrive.service.validations.cliente.cadastrar;

import br.com.fatec.easyDrive.DTO.cliente.DadosCliente;

public interface ValidadorCadastroCliente {
	void validarCadastro(DadosCliente dados);
}
