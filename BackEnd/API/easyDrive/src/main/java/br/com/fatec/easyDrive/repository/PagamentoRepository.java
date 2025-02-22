package br.com.fatec.easyDrive.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.fatec.easyDrive.entity.Pagamento;

public interface PagamentoRepository extends JpaRepository<Pagamento, Long>{

	List<Pagamento> findByReservaId(Long idReserva);

}
