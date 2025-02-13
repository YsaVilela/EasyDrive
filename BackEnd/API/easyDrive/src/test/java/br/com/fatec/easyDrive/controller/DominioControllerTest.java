package br.com.fatec.easyDrive.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ActiveProfiles("test")
public class DominioControllerTest {
	@Autowired
	private TestRestTemplate restTemplate;
	
	@Test
	@DisplayName("Deve retornar 200 quando listar estados")
	void listarEstado() {
		ResponseEntity<?> response = restTemplate.getForEntity("/dominio/estados"
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
	
	@Test
	@DisplayName("Deve retornar 200 quando listar cidades por estado")
	void listarCidadesPorEstado() {
		ResponseEntity<?> response = restTemplate.getForEntity("/dominio/cidades/1"
				,Object.class);

		assertEquals(HttpStatus.OK, response.getStatusCode());
	}
}
