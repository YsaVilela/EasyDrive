package br.com.fatec.easyDrive.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

}
