package br.com.fatec.easyDrive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.entity.Funcionario;

public interface FuncionarioRepository extends JpaRepository<Funcionario, Long>{

}
