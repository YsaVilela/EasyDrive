package br.com.fatec.easyDrive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.easyDrive.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE tb_pessoa RESTART IDENTITY CASCADE", nativeQuery = true)
	void deleteAllAndResetSequence();

	Optional<Pessoa> getByCpf(String cpf);

}
