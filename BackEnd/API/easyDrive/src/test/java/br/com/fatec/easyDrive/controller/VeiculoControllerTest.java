package br.com.fatec.easyDrive.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

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

import br.com.fatec.easyDrive.DTO.veiculo.DadosDetalhamentoVeiculo;
import br.com.fatec.easyDrive.DTO.veiculo.DadosVeiculo;
import br.com.fatec.easyDrive.enumerator.CategoriaEnum;
import br.com.fatec.easyDrive.enumerator.TipoCombustivelEnum;
import br.com.fatec.easyDrive.repository.VeiculoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VeiculoControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	Long iniciarVeiculo() {
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);
		
		ResponseEntity<DadosDetalhamentoVeiculo> response =  restTemplate.postForEntity("/veiculo/cadastrar", dadosVeiculo, DadosDetalhamentoVeiculo.class);
		
		return response.getBody().id();
	}
	
	@BeforeEach
	void iniciar() {
		veiculoRepository.deleteAllAndResetSequence();
	}
	
	@Test
	@DisplayName("Deve retornar 201 quando criado com sucesso")
	void criarCliente() {
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);

		ResponseEntity<?> response = restTemplate.postForEntity("/veiculo/cadastrar",
				dadosVeiculo, Object.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando buscar veiculo existente")
	void buscarVeiculoPorId() {
		Long idVeiculo = iniciarVeiculo();

		ResponseEntity<?> response = restTemplate.getForEntity("/veiculo/buscarId/"+ idVeiculo
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando buscar veiculo não existente")
	void buscarVeiculoPorIdinexistente() {

		ResponseEntity<?> response = restTemplate.getForEntity("/veiculo/buscarId/2000"
				,Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando listar veiculos")
	void listarVeiculos() {
		ResponseEntity<?> response = restTemplate.getForEntity("/veiculo/listarTodos"
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando veiculo atualizado com sucesso")
	void atualizarVeiculo() {
		Long idVeiculo = iniciarVeiculo();

		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);

		ResponseEntity<?> response = restTemplate.exchange("/veiculo/atualizar/" + idVeiculo, HttpMethod.PUT,
				new HttpEntity<>(dadosVeiculo), Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando veiculo atualizado inexistente")
	void atualizarVeiculoInexistente() {
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);

		ResponseEntity<?> response = restTemplate.exchange("/veiculo/atualizar/2000", HttpMethod.PUT,
				new HttpEntity<>(dadosVeiculo), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
//	realizar cadastro de cliente e reserva
//	@Test
//	@DisplayName("Deve retornar 400 quando veiculo atualizado está presente em reserva ativa")
//	void atualizarVeiculoEmReservaAtiva() {
//		Long idVeiculo = iniciarVeiculo();
//
//		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);
//
//		ResponseEntity<?> response = restTemplate.exchange("/veiculo/atualizar/" + idVeiculo, HttpMethod.PUT,
//				new HttpEntity<>(dadosVeiculo), Object.class);
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//	}
	
	@Test
	@DisplayName("Deve retornar ok quando veiculo for suspenso com sucesso")
	void suspenderVeiculo() {
		Long idVeiculo = iniciarVeiculo();

		ResponseEntity<?> response = restTemplate.exchange("/veiculo/suspender/" + idVeiculo, HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando veiculo for suspenso não encontrado")
	void suspenderVeiculoInexistente() {
		ResponseEntity<?> response = restTemplate.exchange("/veiculo/suspender/1000", HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar um ok quando veiculo for ativo com sucesso")
	void ativarVeiculo() {
		Long idVeiculo = iniciarVeiculo();

		ResponseEntity<?> response = restTemplate.exchange("/veiculo/ativar/" + idVeiculo, HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando veiculo for ativo não encontrado")
	void ativarVeiculoInexistente() {
		ResponseEntity<?> response = restTemplate.exchange("/veiculo/ativar/10000", HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar no content quando deletado com sucesso")
	void deletarVeiculo() {
		Long idVeiculo = iniciarVeiculo();
	    ResponseEntity<Void> response = restTemplate.exchange(
	            "/veiculo/deletar/" + idVeiculo, 
	            HttpMethod.DELETE, 
	            null, 
	            Void.class 
	    );

		assertEquals(HttpStatus.NO_CONTENT, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 ao não encontrar veiculo")
	void deletarVeiculoInexistente() {
	    ResponseEntity<Void> response = restTemplate.exchange(
	            "/veiculo/deletar/1000", 
	            HttpMethod.DELETE, 
	            null, 
	            Void.class 
	    );

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

//	realizar cadastro de cliente e reserva
//	@Test
//	@DisplayName("Deve retornar 400 ao tentar deletar veiculo que já realizou uma reserva")
//	void deletarVeiculoComReserva() {
//		Long idVeiculo = iniciarVeiculo();
//	    ResponseEntity<Void> response = restTemplate.exchange(
//        		"/veiculo/deletar/" + idVeiculo, 
//	            HttpMethod.DELETE, 
//	            null, 
//	            Void.class 
//	    );
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//	}
}
