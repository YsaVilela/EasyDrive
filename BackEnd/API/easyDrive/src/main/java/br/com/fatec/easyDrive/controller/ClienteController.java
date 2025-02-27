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

import br.com.fatec.easyDrive.DTO.cliente.DadosCliente;
import br.com.fatec.easyDrive.DTO.cliente.DadosDetalhamentoCliente;
import br.com.fatec.easyDrive.service.ClienteService;
import jakarta.transaction.Transactional;
import jakarta.validation.Valid;

@RestController
@RequestMapping("cliente")
@CrossOrigin(origins = "*", maxAge = 3600)
public class ClienteController {
	
	@Autowired
	private ClienteService service;

	@PostMapping("cadastrar")
	public ResponseEntity<DadosDetalhamentoCliente> cadastrar(
			@RequestBody @Valid DadosCliente dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dados));
	}

	@GetMapping("/buscarId/{id}")
	public ResponseEntity<DadosDetalhamentoCliente> buscarId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}
	
	@GetMapping("/buscarCpf/{cpf}")
	public ResponseEntity<DadosDetalhamentoCliente> buscarId(@PathVariable String cpf) {
		return ResponseEntity.ok(service.buscarPorCpf(cpf));
	}

	@GetMapping("listarTodos")
	public ResponseEntity<Page<DadosDetalhamentoCliente>> listarTodos(
			@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao));
	}

	@PutMapping("atualizar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoCliente> atualizar(
			@RequestBody @Valid DadosCliente dados, @PathVariable Long id) {
		return ResponseEntity.ok(service.atualizar(dados, id));
	}
	
	@PutMapping("suspender/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoCliente> suspender(
			@PathVariable Long id) {
		return ResponseEntity.ok(service.suspender(id));
	}
	
	@PutMapping("ativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoCliente> ativar(
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
