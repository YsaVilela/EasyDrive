package br.com.fatec.easyDrive.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.easyDrive.DTO.pagamento.DadosPagamento;
import br.com.fatec.easyDrive.DTO.reserva.DadosDetalhamentoReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosReserva;
import br.com.fatec.easyDrive.DTO.reserva.DadosResumoReserva;
import br.com.fatec.easyDrive.service.ReservaService;

@RestController
@RequestMapping("reserva")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ReservaController {
	@Autowired
	private ReservaService service;
	
	@PostMapping("gerarOrcamento")
	public ResponseEntity<DadosDetalhamentoReserva> gerarOrcamento(
			@RequestBody @Valid DadosReserva dados) {
		return ResponseEntity.ok(service.gerarOrcamento(dados));
	}
	
	@PostMapping("cadastrar")
	public ResponseEntity<DadosDetalhamentoReserva> cadastrar(
			@RequestBody @Valid DadosReserva dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dados));
	}
	
	@PutMapping("retirada/{idReserva}")
	public ResponseEntity<DadosDetalhamentoReserva> retirada(@PathVariable Long idReserva) {
		return ResponseEntity.status(HttpStatus.OK).body(service.retirada(idReserva));
	}
	
	@PutMapping("devolucao/{idReserva}")
	public ResponseEntity<DadosDetalhamentoReserva> devolucao(@PathVariable Long idReserva) {
		return ResponseEntity.status(HttpStatus.OK).body(service.devolucao(idReserva));
	}
	
	@PostMapping("pagamento")
	public ResponseEntity<DadosDetalhamentoReserva> pagamento(
			@RequestBody @Valid DadosPagamento dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.pagamento(dados));
	}
	
	@PutMapping("finalizar")
	@ResponseStatus(HttpStatus.OK)
	public void finalizar(
			@RequestParam Long idReserva, 
			@RequestParam Boolean veiculoBoasCondicoes, 
			@RequestParam Long quilomentragem
		) {
		service.finalizar(idReserva, veiculoBoasCondicoes, quilomentragem);
	}

	@GetMapping("reservaCliente/{idCliente}")
	public ResponseEntity<Page<DadosResumoReserva>> buscarReservaCliente (
			@PageableDefault(size = 10) Pageable paginacao,
			@PathVariable Long idCliente) {
		return ResponseEntity.ok(service.buscarReservasCliente(idCliente, paginacao));
	}
	
	@GetMapping("reservaVeiculo/{idVeiculo}")
	public ResponseEntity<Page<DadosResumoReserva>> buscarReservaVeiculo (
			@PageableDefault(size = 10) Pageable paginacao,
			@PathVariable Long idVeiculo) {
		return ResponseEntity.ok(service.buscarReservasVeiculo(idVeiculo, paginacao));
	}

}
