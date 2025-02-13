package br.com.fatec.easyDrive.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.easyDrive.entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE tb_endereco RESTART IDENTITY CASCADE", nativeQuery = true)
	void deleteAllAndResetSequence();

}

