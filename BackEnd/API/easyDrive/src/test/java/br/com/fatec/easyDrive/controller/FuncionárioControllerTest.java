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

import br.com.fatec.easyDrive.DTO.endereco.DadosEndereco;
import br.com.fatec.easyDrive.DTO.funcionario.DadosDetalhamentoFuncionario;
import br.com.fatec.easyDrive.DTO.funcionario.DadosFuncionario;
import br.com.fatec.easyDrive.DTO.pessoa.DadosDetalhamentoPessoa;
import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.enumerator.CargoEnum;
import br.com.fatec.easyDrive.repository.EnderecoRepository;
import br.com.fatec.easyDrive.repository.FuncionarioRepository;
import br.com.fatec.easyDrive.repository.PessoaRepository;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class FuncionárioControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;

	@Autowired
	private EnderecoRepository enderecoRepository;

	@Autowired
	private PessoaRepository pessoaRepository;

	@Autowired
	private FuncionarioRepository funcionarioRepository;
	
	Long iniciarPessoa() {
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		
		ResponseEntity<DadosDetalhamentoPessoa> response =  restTemplate.postForEntity("/pessoa/cadastrar", dadosPessoa, DadosDetalhamentoPessoa.class);
		
		return response.getBody().id();
	}

	Long iniciarFuncionario() {
		Long idPessoa = iniciarPessoa();

		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, idPessoa, null);

		ResponseEntity<DadosDetalhamentoFuncionario> response =  restTemplate.postForEntity("/funcionario/cadastrar", dadosFuncionario, DadosDetalhamentoFuncionario.class);

		return response.getBody().id();
	}

	@BeforeEach
	void iniciar() {
		funcionarioRepository.deleteAllAndResetSequence();
		pessoaRepository.deleteAllAndResetSequence();
		enderecoRepository.deleteAllAndResetSequence();
	}

	@Test
	@DisplayName("Deve retornar um created quando criado com sucesso")
	void criarFuncionario() {
		Long idPessoa = iniciarPessoa();

		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, idPessoa, null);

		ResponseEntity<?> response =  restTemplate.postForEntity("/funcionario/cadastrar", 
				dadosFuncionario, Object.class);

		assertEquals(HttpStatus.CREATED, response.getStatusCode());
	}
	
//	@Test
//	@DisplayName("Deve retornar 400 quando tentar cadastrar com idCidade inexistente")
//	void criarFuncionarioComCidadeInexistente() {
//		Long idPessoa = iniciarPessoa();
//		
//		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, idPessoa, null);
//
//		ResponseEntity<?> response =  restTemplate.postForEntity("/funcionario/cadastrar", 
//				dadosFuncionario, Object.class);
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//	}

//	@Test
//	@DisplayName("Deve retornar 400 quando tentar cadastrar com CPF ja cadastrado")
//	void criarFuncionarioComCpfJaCadastrado() {
//		iniciarFuncionario();
//
//		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);
//		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
//
//		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 500000L);
//		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
//		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, dadosPessoa);
//
//		ResponseEntity<?> response =  restTemplate.postForEntity("/funcionario/cadastrar", 
//				dadosFuncionario, Object.class);
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//		assertThat(response.getBody().toString()).contains("Funcionário já cadastrado com esse cpf");
//	}
//
//	@Test
//	@DisplayName("Deve retornar 400 quando tentar cadastrar com email ja cadastrado")
//	void criarFuncionarioComEmailJaCadastrado() {
//		iniciarFuncionario();
//
//		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);
//		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
//
//		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 500000L);
//		DadosPessoa dadosPessoa = new DadosPessoa("teste", "389.498.600-07", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
//		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, dadosPessoa);
//
//		ResponseEntity<?> response =  restTemplate.postForEntity("/funcionario/cadastrar", 
//				dadosFuncionario, Object.class);
//		
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//		assertThat(response.getBody().toString()).contains("Funcionário já cadastrado com esse email");
//	}
//
//	@Test
//	@DisplayName("Deve retornar 400 quando tentar cadastrar com CPF invalido")
//	void criarFuncionarioComCPFInvalidoDigitosIguais() {
//		iniciarFuncionario();
//
//		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);
//
//		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 500000L);
//		DadosPessoa dadosPessoa = new DadosPessoa("teste", "111.111.111-11", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
//		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, dadosPessoa);
//
//		ResponseEntity<?> response =  restTemplate.postForEntity("/funcionario/cadastrar", 
//				dadosFuncionario, Object.class);
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//		assertThat(response.getBody().toString()).contains("CPF Inválido");
//	}
//
//	@Test
//	@DisplayName("Deve retornar 400 quando tentar cadastrar com CPF invalido")
//	void criarFuncionarioComCPFInvalido() {
//		iniciarFuncionario();
//
//		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);
//		LocalDate dataValidadeCNH = LocalDate.now().plusDays(20);
//
//		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 500000L);
//		DadosPessoa dadosPessoa = new DadosPessoa("teste", "123.465.123-11", dataDeNascimento, "(11)12345-6789", "teste1@teste.com", dadosEndereco);
//		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, dadosPessoa);
//
//		ResponseEntity<?> response =  restTemplate.postForEntity("/funcionario/cadastrar", 
//				dadosFuncionario, Object.class);
//
//		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
//		assertThat(response.getBody().toString()).contains("CPF Inválido");
//	}

	@Test
	@DisplayName("Deve retornar 200 quando buscar funcionario existente")
	void buscarFuncionarioPorId() {
		Long idFuncionario = iniciarFuncionario();

		ResponseEntity<?> response = restTemplate.getForEntity("/funcionario/buscarId/"+ idFuncionario
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar 400 quando buscar funcionario não existente")
	void buscarFuncionarioPorIdinexistente() {

		ResponseEntity<?> response = restTemplate.getForEntity("/funcionario/buscarId/2000"
				,Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar 200 quando listar funcionarios")
	void listarFuncionario() {
		ResponseEntity<?> response = restTemplate.getForEntity("/funcionario/listarTodos"
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um ok quando funcionario atualizado com sucesso")
	void atualizarFuncionario() {
		Long idFuncionario = iniciarFuncionario();

		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, null, dadosPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/funcionario/atualizar/" + idFuncionario, HttpMethod.PUT,
				new HttpEntity<>(dadosFuncionario), Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar 400 quando funcionario atualizado inexistente")
	void atualizarFuncionarioInexistente() {
		LocalDate dataDeNascimento = LocalDate.now().minusYears(20);

		DadosEndereco dadosEndereco = new DadosEndereco("00000-000", "teste rua", "2","Ap 1","bairro teste", 1L);
		DadosPessoa dadosPessoa = new DadosPessoa("teste", "753.472.680-85", dataDeNascimento, "(11)12345-6789", "teste@teste.com", dadosEndereco);
		DadosFuncionario dadosFuncionario = new DadosFuncionario(CargoEnum.ATENDENTE, null, dadosPessoa);

		ResponseEntity<?> response = restTemplate.exchange("/funcionario/atualizar/2000", HttpMethod.PUT,
				new HttpEntity<>(dadosFuncionario), Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar ok quando funcionario for suspenso com sucesso")
	void suspenderFuncionario() {
		Long idFuncionario = iniciarFuncionario();

		ResponseEntity<?> response = restTemplate.exchange("/funcionario/suspender/" + idFuncionario, HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar 400 quando funcionario for suspenso não encontrado")
	void suspenderFuncionarioInexistente() {
		ResponseEntity<?> response = restTemplate.exchange("/funcionario/suspender/1000", HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar um ok quando funcionario for ativo com sucesso")
	void ativarFuncionario() {
		Long idFuncionario = iniciarFuncionario();

		ResponseEntity<?> response = restTemplate.exchange("/funcionario/ativar/" + idFuncionario, HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}

	@Test
	@DisplayName("Deve retornar 400 quando funcionario for ativo não encontrado")
	void ativarClienteInexistente() {
		ResponseEntity<?> response = restTemplate.exchange("/funcionario/ativar/10000", HttpMethod.PUT,
				null, Object.class);

		assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
	}

}
