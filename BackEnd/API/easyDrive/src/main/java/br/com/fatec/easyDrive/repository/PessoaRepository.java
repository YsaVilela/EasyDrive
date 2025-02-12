package br.com.fatec.easyDrive.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{

	Optional<Pessoa> getByCpf(String cpf);

}
