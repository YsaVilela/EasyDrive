package br.com.fatec.easyDrive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.easyDrive.entity.Reserva;

public interface ReservaRepository extends JpaRepository<Reserva, Long>{
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE tb_reserva RESTART IDENTITY CASCADE", nativeQuery = true)
	void deleteAllAndResetSequence();

	@Query("SELECT r FROM Reserva r WHERE r.cliente.id = :idCliente")
	List<Reserva> getByClienteId(@Param("idCliente") Long idCliente);

}
