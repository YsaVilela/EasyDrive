package br.com.fatec.easyDrive.controller;

import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.easyDrive.DTO.pessoa.DadosDetalhamentoPessoa;
import br.com.fatec.easyDrive.DTO.pessoa.DadosPessoa;
import br.com.fatec.easyDrive.service.PessoaService;

@RestController
@RequestMapping("pessoa")
@CrossOrigin(origins = "*", maxAge = 3600)
public class PessoaController {
	
	@Autowired
	private PessoaService service;

	@PostMapping("/cadastrar")
	public ResponseEntity<DadosDetalhamentoPessoa> cadastrar(
			@RequestBody @Valid DadosPessoa dados) {
		return ResponseEntity.status(HttpStatus.CREATED).body(service.cadastrar(dados));
	}
	
	@GetMapping("/buscarCPF/{cpf}")
	public ResponseEntity<DadosDetalhamentoPessoa> buscarId(@PathVariable String cpf) {
		return ResponseEntity.ok(service.buscarPorCPF(cpf));
	}
}
