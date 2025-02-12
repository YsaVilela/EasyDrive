package br.com.fatec.easyDrive.entity;

import java.time.LocalDate;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_reserva")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "data_inicio")
    private LocalDate dataInicio;
    
    @Column(name = "data_prevista_fim")
    private LocalDate dataPrevistaFim;

    @Column(name = "data_fim")
    private LocalDate dataFim;

    @Column(name = "orcamento")
    private Double orcamento;
    
    @Column(name = "valor_final")
    private Double valorFinal;
    
    @Column(name = "valor_pago")
    private Double valorPago;

    @Column(name = "status")
    private String status;

    @ManyToOne
    @JoinColumn(name = "fk_cliente")
    private Cliente cliente;

    @ManyToOne
    @JoinColumn(name = "fk_veiculo")
    private Veiculo veiculo;

	public LocalDate getDataInicio() {
		return dataInicio;
	}

	public void setDataInicio(LocalDate dataInicio) {
		this.dataInicio = dataInicio;
	}

	public LocalDate getDataPrevistaFim() {
		return dataPrevistaFim;
	}

	public void setDataPrevistaFim(LocalDate dataPrevistaFim) {
		this.dataPrevistaFim = dataPrevistaFim;
	}

	public LocalDate getDataFim() {
		return dataFim;
	}

	public void setDataFim(LocalDate dataFim) {
		this.dataFim = dataFim;
	}

	public Double getOrcamento() {
		return orcamento;
	}

	public void setOrcamento(Double orcamento) {
		this.orcamento = orcamento;
	}

	public Double getValorFinal() {
		return valorFinal;
	}

	public void setValorFinal(Double valorFinal) {
		this.valorFinal = valorFinal;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Cliente getCliente() {
		return cliente;
	}

	public void setCliente(Cliente cliente) {
		this.cliente = cliente;
	}

	public Veiculo getVeiculo() {
		return veiculo;
	}

	public void setVeiculo(Veiculo veiculo) {
		this.veiculo = veiculo;
	}

	public Long getId() {
		return id;
	}

	public Double getValorPago() {
		return valorPago;
	}

	public void setValorPago(Double valorPago) {
		this.valorPago = valorPago;
	}
	
}
