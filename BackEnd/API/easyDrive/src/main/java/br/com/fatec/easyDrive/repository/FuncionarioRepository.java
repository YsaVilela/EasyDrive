package br.com.fatec.easyDrive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.easyDrive.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE tb_funcionario RESTART IDENTITY CASCADE", nativeQuery = true)
	void deleteAllAndResetSequence();

	@Query("SELECT f FROM Funcionario f WHERE f.pessoa.id = :idPessoa")
	Optional<Funcionario> findByIdPessoa(Long idPessoa);
}
