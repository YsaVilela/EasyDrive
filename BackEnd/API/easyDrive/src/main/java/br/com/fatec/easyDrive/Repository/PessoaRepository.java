package br.com.fatec.easyDrive.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.Entity.Pessoa;

public interface PessoaRepository extends JpaRepository<Pessoa, Long>{}
