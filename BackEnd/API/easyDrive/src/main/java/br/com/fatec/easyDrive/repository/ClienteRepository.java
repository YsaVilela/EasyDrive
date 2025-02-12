package br.com.fatec.easyDrive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import br.com.fatec.easyDrive.entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

	@Query("SELECT c FROM Cliente c WHERE c.pessoa.cpf = :cpf")
	Optional<Cliente> getByCpf(@Param("cpf") String cpf);

	@Query("SELECT c FROM Cliente c WHERE c.pessoa.email = :email")
	Optional<Cliente> getByEmail(@Param("email") String email);

	Optional<Cliente> getByNumeroCNH(@Param("numeroCNH") String numeroCNH);

}
