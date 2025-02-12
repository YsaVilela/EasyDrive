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

@Table(name = "tb_pagamento")
@Entity (name = "Pagamento")
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

	public Double getValor() {
		return valor;
	}

	public void setValor(Double valor) {
		this.valor = valor;
	}

	public FormaPagamentoEnum getFormaPagamento() {
		return formaPagamento;
	}

	public void setFormaPagamento(FormaPagamentoEnum formaPagamento) {
		this.formaPagamento = formaPagamento;
	}

	public String getDataPagamento() {
		return dataPagamento;
	}

	public void setDataPagamento(String dataPagamento) {
		this.dataPagamento = dataPagamento;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Reserva getReserva() {
		return reserva;
	}

	public void setReserva(Reserva reserva) {
		this.reserva = reserva;
	}

	public Long getId() {
		return id;
	}

}
