package br.com.fatec.easyDrive.service.validations.cliente.cadastrar;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import br.com.fatec.easyDrive.DTO.cliente.DadosCliente;
import br.com.fatec.easyDrive.entity.Cliente;
import br.com.fatec.easyDrive.exception.InvalidDataException;
import br.com.fatec.easyDrive.repository.ClienteRepository;

@Component
public class ClienteExistente implements ValidadorCadastroCliente {
	
	@Autowired
	private ClienteRepository clienteRepository;

	@Override
	public void validarCadastro(DadosCliente dados){
		buscarPorCpf(dados.pessoa().cpf());
		buscarPorEmail(dados.pessoa().email());
		buscarPorCNH(dados.numeroCNH());
	}
	
	public void buscarPorCpf(String cpf) {
		Optional<Cliente> cliente = clienteRepository.getByCpf(cpf);
		if(cliente.isPresent()) {
			throw new InvalidDataException ("Cliente já cadastrado com esse cpf");		
		}
	}
	
	public void buscarPorEmail(String email) {
		Optional<Cliente> cliente = clienteRepository.getByEmail(email);
		if(cliente.isPresent()) {
			throw new InvalidDataException ("Cliente já cadastrado com esse email");		
		}
	}
	
	public void buscarPorCNH(String cnh) {
		Optional<Cliente> cliente = clienteRepository.getByNumeroCNH(cnh);
		if(cliente.isPresent()) {
			throw new InvalidDataException ("Cliente já cadastrado com esse número de Carteira Nacional de Habilitação(CNH)");		
		}
	}

}
