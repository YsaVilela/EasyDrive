package br.com.fatec.easyDrive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.easyDrive.entity.Veiculo;

public interface VeiculoRepository extends JpaRepository<Veiculo, Long>{
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE tb_veiculo RESTART IDENTITY CASCADE", nativeQuery = true)
	void deleteAllAndResetSequence();
}
