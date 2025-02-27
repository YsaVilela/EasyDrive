package br.com.fatec.easyDrive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.entity.ServicoReserva;

public interface ServicoReservaRepository  extends JpaRepository<ServicoReserva, Long>{

	List<ServicoReserva> findByReservaId(Long idReserva);

}
