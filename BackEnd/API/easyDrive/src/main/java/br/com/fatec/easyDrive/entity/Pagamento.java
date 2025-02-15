package br.com.fatec.easyDrive.entity;

import br.com.fatec.easyDrive.enumerator.FormaPagamentoEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
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

@Table(name = "tb_pagamento")
@Entity (name = "Pagamento")
@Getter
@Setter
public class Pagamento {

    @Id	@GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "valor")
    private Double valor;

    @Column(name = "forma_pagamento")
    private FormaPagamentoEnum formaPagamento;

    @Column(name = "data_pagamento")
    private String dataPagamento;

    @Column(name = "status")
    private StatusEnum status;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_reserva", nullable = false)
    private Reserva reserva;

}
