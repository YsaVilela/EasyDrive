package br.com.fatec.easyDrive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.transaction.annotation.Transactional;

import br.com.fatec.easyDrive.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{
	@Transactional
	@Modifying
	@Query(value = "TRUNCATE TABLE tb_cliente RESTART IDENTITY CASCADE", nativeQuery = true)
	void deleteAllAndResetSequence();

	@Query("SELECT c FROM Cliente c WHERE c.pessoa.cpf = :cpf")
	Optional<Cliente> getByCpf(@Param("cpf") String cpf);

	@Query("SELECT c FROM Cliente c WHERE c.pessoa.email = :email")
	Optional<Cliente> getByEmail(@Param("email") String email);

	Optional<Cliente> getByNumeroCNH(@Param("numeroCNH") String numeroCNH);

}
