package br.com.fatec.easyDrive.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fatec.easyDrive.DTO.cliente.DadosCliente;
import br.com.fatec.easyDrive.DTO.cliente.DadosDetalhamentoCliente;
import br.com.fatec.easyDrive.DTO.endereco.DadosEndereco;
import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.repository.ClienteRepository;
import br.com.fatec.easyDrive.repository.EnderecoRepository;
import br.com.fatec.easyDrive.repository.PessoaRepository;
import br.com.fatec.easyDrive.repository.ReservaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class ClienteControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private EnderecoRepository enderecoRepository;
	
	@Autowired
	private PessoaRepository pessoaRepository;
	
	@Autowired
	private ClienteRepository clienteRepository;
	
	@Autowired
	private ReservaRepository reservaRepository;
	
	
	Long iniciarCliente() {
		LocalDate data = LocalDate.now().minusDays(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", data, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", data, dadosPessoa);
		
		ResponseEntity<DadosDetalhamentoCliente> response =  restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		
		return response.getBody().id();
	}
	
	@BeforeEach
	void finalizar() {
		reservaRepository.deleteAllAndResetSequence();
		clienteRepository.deleteAllAndResetSequence();
		pessoaRepository.deleteAllAndResetSequence();
		enderecoRepository.deleteAllAndResetSequence();
	}
	
	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarCliente() {
		LocalDate data = LocalDate.now().minusDays(20);
		
		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", data, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", data, dadosPessoa);

		ResponseEntity<?> response = restTemplate.postForEntity("/cliente/cadastrar",
				dadosCliente, Object.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando tentar cadastrar com idCidade inexistente")
	void criarClienteComCidadeInexistente() {
		LocalDate data = LocalDate.now().minusDays(20);
		
		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 500000L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", data, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", data, dadosPessoa);

		ResponseEntity<?> response = restTemplate.postForEntity("/cliente/cadastrar",
				dadosCliente, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando buscar cliente existente")
	void buscarClientePorId() {
		Long idCliente = iniciarCliente();

		ResponseEntity<?> response = restTemplate.getForEntity("/cliente/buscarId/"+ idCliente
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando buscar cliente não existente")
	void buscarClientePorIdinexistente() {

		ResponseEntity<?> response = restTemplate.getForEntity("/cliente/buscarId/2000"
				,Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando listar clientes")
	void listarCliente() {
		ResponseEntity<?> response = restTemplate.getForEntity("/cliente/listarTodos"
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar um ok quando cliente atualizado com sucesso")
	void atualizarCliente() {
		Long idCliente = iniciarCliente();

		LocalDate data = LocalDate.now().minusDays(20);
		
		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", data, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", data, dadosPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/" + idCliente, HttpMethod.PUT,
				new HttpEntity<>(dadosCliente), Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando cliente atualizado inexistente")
	void atualizarClienteInexistente() {
		LocalDate data = LocalDate.now().minusDays(20);
		
		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", data, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", data, dadosPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/2000", HttpMethod.PUT,
				new HttpEntity<>(dadosCliente), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar ok quando cliente for suspenso com sucesso")
	void suspenderCliente() {
		Long idCliente = iniciarCliente();

		ResponseEntity<?> response = restTemplate.exchange("/cliente/suspender/" + idCliente, HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando cliente for suspenso não encontrado")
	void suspenderClienteInexistente() {
		ResponseEntity<?> response = restTemplate.exchange("/cliente/suspender/1000", HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar um ok quando cliente for ativo com sucesso")
	void ativarCliente() {
		Long idCliente = iniciarCliente();

		ResponseEntity<?> response = restTemplate.exchange("/cliente/ativar/" + idCliente, HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando cliente for ativo não encontrado")
	void ativarClienteInexistente() {
		ResponseEntity<?> response = restTemplate.exchange("/cliente/ativar/10000", HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar no content quando deletado com sucesso")
	void deletarCliente() {
		Long idCliente = iniciarCliente();
	    ResponseEntity<Void> response = restTemplate.exchange(
	            "/cliente/deletar/" + idCliente, 
	            HttpMethod.DELETE, 
	            null, 
	            Void.class 
	    );

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar 400 ao não encontrar cliente")
	void deletarClienteInexistente() {
	    ResponseEntity<Void> response = restTemplate.exchange(
	            "/cliente/deletar/1000", 
	            HttpMethod.DELETE, 
	            null, 
	            Void.class 
	    );

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

//	realizar cadastro de carro e reserva
//	@Test
//	@DisplayName("Deve retornar 400 ao tentar deletar cliente que já realizou uma reserva")
//	void deletarClienteComReserva() {
//		Long idCliente = iniciarCliente();
//	    ResponseEntity<Void> response = restTemplate.exchange(
//        		"/cliente/deletar/" + idCliente, 
//	            HttpMethod.DELETE, 
//	            null, 
//	            Void.class 
//	    );
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//	}

}
