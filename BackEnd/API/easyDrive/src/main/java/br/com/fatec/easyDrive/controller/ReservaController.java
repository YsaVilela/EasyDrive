package br.com.fatec.easyDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.easyDrive.DTO.reserva.DadosDetalhamentoReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosReserva;
import br.com.fatec.easyDrive.service.ReservaService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("reserva")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReservaController {
	@Autowired
	private ReservaService service;
	
	@GetMapping("/gerarOrcamento")
	public ResponseEntity<DadosDetalhamentoReserva> gerarOrcamento(
			@RequestBody @Valid DadosReserva dados) {
		return ResponseEntity.ok(service.gerarOrcamento(dados));
	}
	
	@PostMapping("cadastrar")
	public ResponseEntity<DadosDetalhamentoReserva> cadastrar(
			@RequestBody @Valid DadosReserva dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dados));
	}
	
	@DeleteMapping("deletar/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}

}
