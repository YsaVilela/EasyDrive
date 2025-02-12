package br.com.fatec.easyDrive.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.fatec.easyDrive.DTO.cidade.DadosDetalhamentoCidade;
import br.com.fatec.easyDrive.DTO.estado.DadosDetalhamentoEstado;
import br.com.fatec.easyDrive.repository.CidadeRepository;
import br.com.fatec.easyDrive.repository.EstadoRepository;

@Service
public class DominioService {
	@Autowired
	private CidadeRepository cidadeRepository;
	
	@Autowired
	private EstadoRepository estadoRepository;
	
	public List<DadosDetalhamentoEstado> listarEstados() {
	    return estadoRepository.findAll()
	        .stream()
	        .map(estado -> new DadosDetalhamentoEstado(estado))
	        .collect(Collectors.toList());
	}
	
	public List<DadosDetalhamentoCidade> listarCidadePorEstado(Long idEstado){
		return cidadeRepository.findByEstadoId(idEstado)
				.stream()
				.map(cidade -> new DadosDetalhamentoCidade(cidade))
				.collect(Collectors.toList());
	}

}
