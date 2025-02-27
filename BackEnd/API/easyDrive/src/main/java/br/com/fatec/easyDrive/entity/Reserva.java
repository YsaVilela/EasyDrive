package br.com.fatec.easyDrive.entity;

import java.time.LocalDateTime;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

import lombok.Getter;
import lombok.Setter;

import br.com.fatec.easyDrive.enumerator.StatusEnum;

@Entity
@Table(name = "tb_reserva")
@Getter
@Setter
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_inicio")
    private LocalDateTime dataInicio;
    
    @Column(name = "data_retirada")
    private LocalDateTime dataRetirada;

    @Column(name = "data_fim")
    private LocalDateTime dataFim;
    
    @Column(name = "data_devolucao")
    private LocalDateTime dataDevolucao;

    @Column(name = "orcamento")
    private Double orcamento;
    
    @Column(name = "orcamento_final")
    private Double orcamentoFinal;
    
    @Column(name = "valor_final")
    private Double valorFinal;
    
    @Column(name = "valor_pago")
    private Double valorPago;

    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    private StatusEnum status;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    private Veiculo veiculo;
	
}
