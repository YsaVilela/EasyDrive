package br.com.fatec.easyDrive.controller;

import jakarta.transaction.Transactional;
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
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.easyDrive.DTO.funcionario.DadosDetalhamentoFuncionario;
import br.com.fatec.easyDrive.DTO.funcionario.DadosFuncionario;
import br.com.fatec.easyDrive.service.FuncionarioService;

@RestController
@RequestMapping("funcionario")
@CrossOrigin(origins = "*", maxAge = 3600)
public class FuncionarioController {
	
	@Autowired
	private FuncionarioService service;

	@PostMapping("cadastrar")
	public ResponseEntity<DadosDetalhamentoFuncionario> cadastrar(
			@RequestBody @Valid DadosFuncionario dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dados));
	}
	
	@GetMapping("/buscarId/{id}")
	public ResponseEntity<DadosDetalhamentoFuncionario> buscarId(@PathVariable Long id) {
		return ResponseEntity.ok(service.buscarPorId(id));
	}

	@GetMapping("listarTodos")
	public ResponseEntity<Page<DadosDetalhamentoFuncionario>> listarTodos(
			@PageableDefault(size = 10) Pageable paginacao) {
		return ResponseEntity.ok(service.listarTodos(paginacao));
	}

	@PutMapping("atualizar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoFuncionario> atualizar(
			@RequestBody @Valid DadosFuncionario dados, @PathVariable Long id) {
		return ResponseEntity.ok(service.atualizar(dados, id));
	}
	
	@PutMapping("suspender/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoFuncionario> suspender(
			@PathVariable Long id) {
		return ResponseEntity.ok(service.suspender(id));
	}
	
	@PutMapping("ativar/{id}")
	@Transactional
	public ResponseEntity<DadosDetalhamentoFuncionario> ativar(
			@PathVariable Long id) {
		return ResponseEntity.ok(service.ativar(id));
	}

	
}
