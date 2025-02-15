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
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

import br.com.fatec.easyDrive.DTO.cliente.DadosCliente;
import br.com.fatec.easyDrive.DTO.cliente.DadosDetalhamentoCliente;
import br.com.fatec.easyDrive.DTO.endereco.DadosEndereco;
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
public class ReservaControllerTest {
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
	
	Long iniciarCliente() {
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);
		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosCliente dadosCliente = new DadosCliente("123456789", dataValidadeCNH, dadosPessoa);
		
		ResponseEntity<DadosDetalhamentoCliente> response =  restTemplate.postForEntity("/cliente/cadastrar", dadosCliente, DadosDetalhamentoCliente.class);
		
		return response.getBody().id();
	}
	
	Long iniciarVeiculo() {
		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);
		
		ResponseEntity<DadosDetalhamentoVeiculo> response =  restTemplate.postForEntity("/veiculo/cadastrar", dadosVeiculo, DadosDetalhamentoVeiculo.class);
		
		restTemplate.exchange("/veiculo/ativar/" + response.getBody().id(), HttpMethod.PUT, null, Object.class);
		
		return response.getBody().id();
	}
	
	Long iniciarReserva(Long idCliente, Long idVeiculo) {
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente,idVeiculo, servicos);
		
		ResponseEntity<DadosDetalhamentoReserva> response =  restTemplate.postForEntity("/reserva/cadastrar", dadosReserva, DadosDetalhamentoReserva.class);
		
		return response.getBody().id();
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
	@DisplayName("Deve retornar 200 quando gerado um orcamento com sucesso")
	void gerarOrcamento() {
		Long idCliente = iniciarCliente();
		Long idVeiculo = iniciarVeiculo();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente,idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/gerarOrcamento",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando ao gerar um orcamento não encontrar o cliente")
	void gerarOrcamentoClienteInexistente() {
		Long idVeiculo = iniciarVeiculo();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, 1000L ,idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/gerarOrcamento",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Cliente com id 1000 não encontrado");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando ao gerar um orcamento não encontrar o veiculo")
	void gerarOrcamentoVeiculoInexistente() {
		Long idCliente = iniciarCliente();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente, 1000L, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/gerarOrcamento",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Veiculo com id 1000 não encontrado");
	}
	
	@Test
	@DisplayName("Deve retornar 400 ao gerar um orcamento a data final for inferior a data inicio")
	void gerarOrcamentoDataFinalInferiorInicio() {
		Long idCliente = iniciarCliente();
		Long idVeiculo = iniciarVeiculo();

		LocalDateTime horaInicial = LocalDateTime.now().plusDays(3);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente, idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/gerarOrcamento",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Data início da reserva não pode ser após data final");
	}

	@Test
	@DisplayName("Deve retornar created quando criado com sucesso")
	void cadastrarReservaSucesso() {
		Long idCliente = iniciarCliente();
		Long idVeiculo = iniciarVeiculo();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente,idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/cadastrar",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando criado não encontrar o cliente")
	void cadastrarReservaClienteInexistente() {
		Long idVeiculo = iniciarVeiculo();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, 1000L ,idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/cadastrar",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Cliente com id 1000 não encontrado");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando criado não encontrar o veiculo")
	void cadastrarReservaVeiculoInexistente() {
		Long idCliente = iniciarCliente();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente, 1000L, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/cadastrar",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Veiculo com id 1000 não encontrado");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando criado com data final inferior a data inicio")
	void cadastrarReservaDataFinalInferiorInicio() {
		Long idCliente = iniciarCliente();
		Long idVeiculo = iniciarVeiculo();

		LocalDateTime horaInicial = LocalDateTime.now().plusDays(3);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente, idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/cadastrar",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Data início da reserva não pode ser após data final");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando veiculo não estiver ativo")
	void cadastrarReservaVeiculoDiferenteAtivo() {
		Long idCliente = iniciarCliente();

		DadosVeiculo dadosVeiculo = new DadosVeiculo("abc-1234", "etios", "toyota", 2018, 180000L, CategoriaEnum.ECONOMICO, "preto", TipoCombustivelEnum.F, 100.00);
		Long idVeiculo =  restTemplate.postForEntity("/veiculo/cadastrar", dadosVeiculo, DadosDetalhamentoVeiculo.class).getBody().id();
		
		LocalDateTime horaInicial = LocalDateTime.now().plusHours(2);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente,idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/cadastrar",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Veículo não está ativo para reservas");
	}
	
	@Test
	@DisplayName("Deve retornar 400 quando cliente já possuir outra reserva para a mesma data")
	void cadastrarReservaClienteJaPossuiOutraReservas() {
		Long idCliente = iniciarCliente();
		Long idVeiculo = iniciarVeiculo();
		
		iniciarReserva(idCliente, idVeiculo);

		LocalDateTime horaInicial = LocalDateTime.now().plusHours(1);
		LocalDateTime horaFinal = LocalDateTime.now().plusDays(1);
		List<ServicoEnum> servicos = List.of(ServicoEnum.ASSISTENCIA_24H, ServicoEnum.LIMPEZA);

		DadosReserva dadosReserva = new DadosReserva(horaInicial,horaFinal, idCliente,idVeiculo, servicos);

		ResponseEntity<?> response = restTemplate.postForEntity("/reserva/cadastrar",
				dadosReserva, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
		assertThat(response.getBody().toString()).contains("Já possui uma reserva cadastrada para a data selecionada");
	}
}
