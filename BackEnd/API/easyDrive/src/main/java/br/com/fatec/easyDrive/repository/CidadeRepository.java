package br.com.fatec.easyDrive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.entity.Cidade;

public interface CidadeRepository extends JpaRepository<Cidade, Long>{
	
	List<Cidade> findByEstadoId(Long idEstado);

}
