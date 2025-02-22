package br.com.fatec.easyDrive.entity;

import java.time.LocalDateTime;

import jakarta.persistence.CascadeType;
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

import br.com.fatec.easyDrive.enumerator.FormaPagamentoEnum;

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

    @Enumerated(EnumType.STRING)
    @Column(name = "forma_pagamento")
    private FormaPagamentoEnum formaPagamento;

    @Column(name = "data_pagamento")
    private LocalDateTime dataPagamento;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "fk_reserva", nullable = false)
    private Reserva reserva;

}
