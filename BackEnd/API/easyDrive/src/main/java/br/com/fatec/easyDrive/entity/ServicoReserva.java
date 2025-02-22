package br.com.fatec.easyDrive.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import br.com.fatec.easyDrive.enumerator.ServicoEnum;

@Table(name = "tb_servico_reserva")
@Entity (name = "ServicoReserva")
@Getter
@Setter
public class ServicoReserva {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
    
	@Column(name = "nome") 
    private ServicoEnum nome;
	
	@Column(name = "valor") 
    private Double valor;
	
    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_reserva", nullable = false)
    private Reserva reserva;

}
