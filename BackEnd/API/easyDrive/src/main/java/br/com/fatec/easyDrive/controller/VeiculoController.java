package br.com.fatec.easyDrive.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.easyDrive.DTO.veiculo.DadosDetalhamentoVeiculo;
import br.com.fatec.easyDrive.DTO.veiculo.DadosVeiculo;
import br.com.fatec.easyDrive.service.VeiculoService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("veiculo")
@CrossOrigin(origins = "*", maxAge = 3600)
public class VeiculoController {
	@Autowired
	private VeiculoService service;

	@PostMapping("cadastrar")
	public ResponseEntity<DadosDetalhamentoVeiculo> cadastrar(
			@RequestBody @Valid DadosVeiculo dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dados));
	}
	
	@GetMapping("/buscarId/{id}")
	public ResponseEntity<DadosDetalhamentoVeiculo> buscarId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping("listarTodos")
	public ResponseEntity<Page<DadosDetalhamentoVeiculo>> listarTodos(
			@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao));
	}
	
	@PutMapping("atualizar/{idVeiculo}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoVeiculo> atualizar(
			@RequestBody @Valid DadosVeiculo dados, @PathVariable Long idVeiculo) {
		return ResponseEntity.ok(service.atualizar(dados, idVeiculo));
	}
	
	@PutMapping("suspender/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoVeiculo> suspender(
			@PathVariable Long id) {
		return ResponseEntity.ok(service.suspender(id));
	}
	
	@PutMapping("ativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoVeiculo> ativar(
			@PathVariable Long id) {
		return ResponseEntity.ok(service.ativar(id));
	}
	
	@DeleteMapping("deletar/{id}")
	@Transactional
	public ResponseEntity<HttpStatus> deletar(@PathVariable Long id) {
		service.deletar(id);
		return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
	}
}
