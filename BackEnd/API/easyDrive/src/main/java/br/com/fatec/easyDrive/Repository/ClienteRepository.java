package br.com.fatec.easyDrive.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.Entity.Cliente;

public interface ClienteRepository extends JpaRepository<Cliente, Long>{

}
