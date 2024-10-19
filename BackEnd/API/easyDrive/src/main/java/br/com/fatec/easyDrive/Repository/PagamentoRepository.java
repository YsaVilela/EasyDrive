package br.com.fatec.easyDrive.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.Entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

}
