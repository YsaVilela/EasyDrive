package br.com.fatec.easyDrive.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.Entity.Endereco;

public interface EnderecoRepository extends JpaRepository<Endereco, Long>{}

