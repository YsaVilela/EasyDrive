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
	private VeiculoRepository veiculoRepository;

	@Autowired
	private ReservaRepository reservaRepository;
	
	Long iniciarPessoa() {
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		
		ResponseEntity<DadosDetalhamentoPessoa> response =  restTemplate.postForEntity("/pessoa/cadastrar", dadosPessoa, DadosDetalhamentoPessoa.class);
		
		return response.getBody().id();
	}
	
	
	Long iniciarCliente() {
		Long idPessoa = iniciarPessoa();
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		
		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, idPessoa, null);
		
		ResponseEntity<DadosDetalhamentoCliente> response =  restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		
		return response.getBody().id();
	}
	
	Long iniciarVeiculo() {
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);
		
		ResponseEntity<DadosDetalhamentoVeiculo> response =  restTemplate.postForEntity("/veiculo/cadastrar", dadosVeiculo, DadosDetalhamentoVeiculo.class);
		
		restTemplate.exchange("/veiculo/ativar/" + response.getBody().id(), HttpMethod.PUT, null, Object.class);
		
		return response.getBody().id();
	}
	
	Long iniciarReserva(Long idCliente) {
		Long idVeiculo = iniciarVeiculo();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente,idVeiculo, servicos);
		
		ResponseEntity<DadosDetalhamentoReserva> response =  restTemplate.postForEntity("/reserva/cadastrar", dadosReserva, DadosDetalhamentoReserva.class);
		
		return response.getBody().id();
	}
	
	@BeforeEach
	void finalizar() {
		reservaRepository.deleteAllAndResetSequence();
		veiculoRepository.deleteAllAndResetSequence();
		clienteRepository.deleteAllAndResetSequence();
		pessoaRepository.deleteAllAndResetSequence();
		enderecoRepository.deleteAllAndResetSequence();
	}
	
	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarCliente() {
		Long idPessoa = iniciarPessoa();
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);

		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, idPessoa, null);

		ResponseEntity<?> response = restTemplate.postForEntity("/cliente/cadastrar",
				dadosCliente, Object.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando tentar cadastrar com pessoa dupliciada")
	void criarClienteComPessoaJaCadastrada() {
		Long idPessoa = iniciarPessoa();

		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		
		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, idPessoa, null);

		restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, Object.class);

		ResponseEntity<?> response = restTemplate.postForEntity("/cliente/cadastrar",
				dadosCliente, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Cliente já possui cadastro");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando ao cadastrar não encontrar pessoa")
	void criarClienteComPessoainexistente() {
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		
		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, 100L, null);

		restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, Object.class);

		ResponseEntity<?> response = restTemplate.postForEntity("/cliente/cadastrar",
				dadosCliente, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Pessoa com id 100 não encontrado");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando tentar cadastrar com CNH ja cadastrado")
	void criarClienteComCNHJaCadastrado() {
		iniciarCliente();

		Long idPessoa = iniciarPessoa();
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);

		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, idPessoa, null);

		ResponseEntity<?> response = restTemplate.postForEntity("/cliente/cadastrar",
				dadosCliente, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Cliente já cadastrado com esse número de Carteira Nacional de Habilitação(CNH)");
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
	@DisplayName("Deve retornar 200 quando buscar cliente por CPF")
	void buscarClientePorCPF() {
		iniciarCliente();

		ResponseEntity<?> response = restTemplate.getForEntity("/cliente/buscarCpf/753.472.680-85"
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando buscar cliente por CPF inexistente")
	void buscarClientePorCPFInexistente() {
		ResponseEntity<?> response = restTemplate.getForEntity("/cliente/buscarCpf/753.472.680-85"
				,Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Cliente com cpf 753.472.680-85 não encontrado");
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

		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		
		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, null, dadosPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/" + idCliente, HttpMethod.PUT,
				new HttpEntity<>(dadosCliente), Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando cliente atualizado inexistente")
	void atualizarClienteInexistente() {
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		
		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, null, dadosPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/2000", HttpMethod.PUT,
				new HttpEntity<>(dadosCliente), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar um ok quando ao atualizar cliente cpf já cadastrado")
	void atualizarClienteCpfJaExistente() {
		iniciarCliente();
		
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "191.088.900-80", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
		
		ResponseEntity<DadosDetalhamentoPessoa> responsePessoa =  restTemplate.postForEntity("/pessoa/cadastrar", dadosPessoa, DadosDetalhamentoPessoa.class);
		
		Long idPessoa = responsePessoa.getBody().id();
		
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		DadosCliente dadosCliente = new DadosCliente("123456788", dataValidadeCNH, idPessoa, null);
		ResponseEntity<DadosDetalhamentoCliente> responseCliente = restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		Long idCliente = responseCliente.getBody().id();		
		
		DadosPessoa dadosAtualizarPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
		DadosCliente dadosAtualizarCliente = new DadosCliente("123456788", dataValidadeCNH, null, dadosAtualizarPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/" + idCliente, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCliente), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("CPF 753.472.680-85 já cadastrado");
	}
	
	@Test
	@DisplayName("Deve retornar um 400 quando ao atualizar cliente email já cadastrado")
	void atualizarClienteEmailJaExistente() {
		iniciarCliente();
		
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "191.088.900-80", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
		
		ResponseEntity<DadosDetalhamentoPessoa> responsePessoa =  restTemplate.postForEntity("/pessoa/cadastrar", dadosPessoa, DadosDetalhamentoPessoa.class);
		
		Long idPessoa = responsePessoa.getBody().id();
		
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		DadosCliente dadosCliente = new DadosCliente("987654321", dataValidadeCNH, idPessoa, null);
		ResponseEntity<DadosDetalhamentoCliente> responseCliente = restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		Long idCliente = responseCliente.getBody().id();
				
		DadosPessoa dadosAtualizarPessoa = new DadosPessoa("teste", "191.088.900-80", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosAtualizarCliente = new DadosCliente("987654321", dataValidadeCNH, null, dadosAtualizarPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/" + idCliente, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCliente), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Email teste@teste.com já cadastrado");
	}
	
	@Test
	@DisplayName("Deve retornar um 400 quando ao atualizar cliente CNH já cadastrado")
	void atualizarClienteCNHJaExistente() {
		iniciarCliente();
		
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "191.088.900-80", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
		
		ResponseEntity<DadosDetalhamentoPessoa> responsePessoa =  restTemplate.postForEntity("/pessoa/cadastrar", dadosPessoa, DadosDetalhamentoPessoa.class);
		
		Long idPessoa = responsePessoa.getBody().id();
		
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		DadosCliente dadosCliente = new DadosCliente("987654321", dataValidadeCNH, idPessoa, null);
		ResponseEntity<DadosDetalhamentoCliente> responseCliente = restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		Long idCliente = responseCliente.getBody().id();
				
		DadosPessoa dadosAtualizarPessoa = new DadosPessoa("teste", "191.088.900-80", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
		DadosCliente dadosAtualizarCliente = new DadosCliente("123456789", dataValidadeCNH, null, dadosAtualizarPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/" + idCliente, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCliente), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Cliente já cadastrado com esse número de Carteira Nacional de Habilitação(CNH)");
	}
	
	@Test
	@DisplayName("Deve retornar um ok quando ao atualizar cliente pessoa já cadastrado")
	void atualizarClientePessoaJaExistente() {
		iniciarCliente();
		
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "191.088.900-80", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
		
		ResponseEntity<DadosDetalhamentoPessoa> responsePessoa =  restTemplate.postForEntity("/pessoa/cadastrar", dadosPessoa, DadosDetalhamentoPessoa.class);
		
		Long idPessoa = responsePessoa.getBody().id();
		
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
		DadosCliente dadosCliente = new DadosCliente("123456788", dataValidadeCNH, idPessoa, null);
		ResponseEntity<DadosDetalhamentoCliente> responseCliente = restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		
		Long idCliente = responseCliente.getBody().id();		
		DadosPessoa dadosAtualizarPessoa = new DadosPessoa("teste", "191.088.900-80", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosAtualizarCliente = new DadosCliente("123456788", dataValidadeCNH, null, dadosAtualizarPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/cliente/atualizar/" + idCliente, HttpMethod.PUT,
				new HttpEntity<>(dadosAtualizarCliente), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Email teste@teste.com já cadastrado");
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
	    ResponseEntity<?> response = restTemplate.exchange(
	            "/cliente/deletar/" + idCliente, 
	            HttpMethod.DELETE, 
	            null, 
	            Object.class 
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

	@Test
	@DisplayName("Deve retornar 400 ao tentar deletar cliente que já realizou uma reserva")
	void deletarClienteComReserva() {
		Long idCliente = iniciarCliente();
		iniciarReserva(idCliente);
		
	    ResponseEntity<?> response = restTemplate.exchange(
        		"/cliente/deletar/" + idCliente, 
	            HttpMethod.DELETE, 
	            null, 
	            Object.class 
	    );

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Não é possivel excluir cliente que já realizou reserva");
	}

}
