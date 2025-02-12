package br.com.fatec.easyDrive.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.fatec.easyDrive.DTO.cidade.DadosDetalhamentoCidade;
import br.com.fatec.easyDrive.DTO.estado.DadosDetalhamentoEstado;
import br.com.fatec.easyDrive.service.DominioService;

@RestController
@RequestMapping("dominio")
@CrossOrigin(origins = "*", maxAge = 3600)
public class DominioControler {
	
	@Autowired
	private DominioService service;
	
	@GetMapping("/estados")
	public ResponseEntity<List<DadosDetalhamentoEstado>> listarEstados() {
		return ResponseEntity.ok(service.listarEstados());
	}

	@GetMapping("/cidades/{idEstado}")
	public ResponseEntity<List<DadosDetalhamentoCidade>> listarCidade(@PathVariable Long idEstado) {
		return ResponseEntity.ok(service.listarCidadePorEstado(idEstado));
	}
}
