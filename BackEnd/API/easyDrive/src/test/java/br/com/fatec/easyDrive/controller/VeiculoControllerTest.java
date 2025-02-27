package br.com.fatec.easyDrive.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

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
import br.com.fatec.easyDrive.DTO.pessoa.DadosDetalhamentoPessoa;
import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.DTO.reserva.DadosDetalhamentoReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosReserva;
import br.com.fatec.easyDrive.DTO.veiculo.DadosDetalhamentoVeiculo;
import br.com.fatec.easyDrive.DTO.veiculo.DadosVeiculo;
import br.com.fatec.easyDrive.enumerator.CategoriaEnum;
import br.com.fatec.easyDrive.enumerator.ServicoEnum;
import br.com.fatec.easyDrive.enumerator.TipoCombustivelEnum;
import br.com.fatec.easyDrive.repository.ClienteRepository;
import br.com.fatec.easyDrive.repository.EnderecoRepository;
import br.com.fatec.easyDrive.repository.PessoaRepository;
import br.com.fatec.easyDrive.repository.ReservaRepository;
import br.com.fatec.easyDrive.repository.VeiculoRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class VeiculoControllerTest {
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
	
	@Autowired
	private VeiculoRepository veiculoRepository;
	
	Long iniciarVeiculo() {
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);
		
		ResponseEntity<DadosDetalhamentoVeiculo> response =  restTemplate.postForEntity("/veiculo/cadastrar", dadosVeiculo, DadosDetalhamentoVeiculo.class);
		
		return response.getBody().id();
	}
	
	Long iniciarCliente() {
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		
		ResponseEntity<DadosDetalhamentoPessoa> response =  restTemplate.postForEntity("/pessoa/cadastrar", dadosPessoa, DadosDetalhamentoPessoa.class);
				
		Long idPessoa = response.getBody().id();
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		
		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, idPessoa, null);
		
		ResponseEntity<DadosDetalhamentoCliente> responseCliente =  restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		
		return responseCliente.getBody().id();
	}
	
	
	Long iniciarReserva(Long idVeiculo) {
		Long idCliente = iniciarCliente();
		
		restTemplate.exchange("/veiculo/ativar/" + idVeiculo, HttpMethod.PUT,
				null, Object.class);
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente, idVeiculo, servicos);
		
		ResponseEntity<DadosDetalhamentoReserva> response =  restTemplate.postForEntity("/reserva/cadastrar", dadosReserva, DadosDetalhamentoReserva.class);
		
		Long idReserva = response.getBody().id();
		
		restTemplate.exchange(
				"/reserva/retirada/" + idReserva,
				HttpMethod.PUT,
				null,
				Object.class);
		
		return idReserva;
	}
	
	@BeforeEach
	void iniciar() {
		reservaRepository.deleteAllAndResetSequence();
		veiculoRepository.deleteAllAndResetSequence();
		clienteRepository.deleteAllAndResetSequence();
		pessoaRepository.deleteAllAndResetSequence();
		enderecoRepository.deleteAllAndResetSequence();
	}
	
	@Test
	@DisplayName("Deve retornar 201 quando criado com sucesso")
	void criarVeiculo() {
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);

		ResponseEntity<?> response = restTemplate.postForEntity("/veiculo/cadastrar",
				dadosVeiculo, Object.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 201 quando criado com sucesso")
	void criarVeiculoPlacaRepetida() {
		iniciarVeiculo();
		
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);

		ResponseEntity<?> response = restTemplate.postForEntity("/veiculo/cadastrar",
				dadosVeiculo, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Veiculo com placa abc-1234 já cadastrado");
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
	@DisplayName("Deve retornar 200 quando buscar veiculo por placa")
	void buscarVeiculoPorPlaca() {
		iniciarVeiculo();

		ResponseEntity<?> response = restTemplate.getForEntity("/veiculo/buscarPlaca/abc-1234"
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando buscar veiculo por placa inexistente")
	void buscarVeiculoPorPlacaInexistente() {

		ResponseEntity<?> response = restTemplate.getForEntity("/veiculo/buscarPlaca/abc-1234"
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
	
	@Test
	@DisplayName("Deve retornar 400 quando veiculo atualizado está presente em reserva ativa")
	void atualizarVeiculoEmReservaAtiva() {
		Long idVeiculo = iniciarVeiculo();
		iniciarReserva(idVeiculo);

		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);

		ResponseEntity<?> response = restTemplate.exchange("/veiculo/atualizar/" + idVeiculo, HttpMethod.PUT,
				new HttpEntity<>(dadosVeiculo), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Veículo " + idVeiculo + " não pode ser atualizado pois está presente em uma reserva ativa.");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando ao atualizar veiculo placa ja exista no sistema")
	void atualizarVeiculoPlacaRepetida() {
		iniciarVeiculo();
		
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1235", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);
		
		ResponseEntity<DadosDetalhamentoVeiculo> responseVeiculo =  restTemplate.postForEntity("/veiculo/cadastrar", dadosVeiculo, DadosDetalhamentoVeiculo.class);
		Long idVeiculo = responseVeiculo.getBody().id();
		
		DadosVeiculo dadosVeiculoAtualizado = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);

		ResponseEntity<?> response = restTemplate.exchange("/veiculo/atualizar/" + idVeiculo, HttpMethod.PUT,
				new HttpEntity<>(dadosVeiculoAtualizado), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Veiculo com placa abc-1234 já cadastrado");
	}
	
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

	@Test
	@DisplayName("Deve retornar 400 ao tentar deletar veiculo que já realizou uma reserva")
	void deletarVeiculoComReserva() {
		Long idVeiculo = iniciarVeiculo();
		iniciarReserva(idVeiculo);

	    ResponseEntity<?> response = restTemplate.exchange(
        		"/veiculo/deletar/" + idVeiculo, 
	            HttpMethod.DELETE, 
	            null, 
	            Object.class 
	    );

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Não é possivel excluir veiculo que já participou de uma reserva");
	}
}
