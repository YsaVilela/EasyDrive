package br.com.fatec.easyDrive.entity;

import br.com.fatec.easyDrive.enumerator.TipoCombustivelEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Table(name = "tb_veiculo")
@Entity (name = "Veiculo")
public class Veiculo {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "placa", unique = true) 
	private String placa;
	
	@Column(name = "modelo") 
	private String modelo;
	
	@Column(name = "marca") 
	private String marca;
	
	@Column(name = "ano") 
	private Integer ano;
	
	@Column(name = "quilometragem") 
	private Long quilometragem;
	
	@Column(name = "categoria") 
	private String categoria;
	
	@Column(name = "tipo_combustivel") 
	private TipoCombustivelEnum tipoCombustivel;
	
	@Column(name = "cor") 
	private String cor;
	
	@Column(name = "valor_diaria")
	private Double valorDiaria;
	
	@Column(name = "status") 
	private String status;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_endereco")
	protected Endereco endereco;

	public String getPlaca() {
		return placa;
	}

	public void setPlaca(String placa) {
		this.placa = placa;
	}

	public String getModelo() {
		return modelo;
	}

	public void setModelo(String modelo) {
		this.modelo = modelo;
	}

	public String getMarca() {
		return marca;
	}

	public void setMarca(String marca) {
		this.marca = marca;
	}

	public Integer getAno() {
		return ano;
	}

	public void setAno(Integer ano) {
		this.ano = ano;
	}

	public Long getQuilometragem() {
		return quilometragem;
	}

	public void setQuilometragem(Long quilometragem) {
		this.quilometragem = quilometragem;
	}

	public String getCategoria() {
		return categoria;
	}

	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}

	public TipoCombustivelEnum getTipoCombustivel() {
		return tipoCombustivel;
	}

	public void setTipoCombustivel(TipoCombustivelEnum tipoCombustivel) {
		this.tipoCombustivel = tipoCombustivel;
	}

	public String getCor() {
		return cor;
	}

	public void setCor(String cor) {
		this.cor = cor;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}

	public Endereco getEndereco() {
		return endereco;
	}

	public void setEndereco(Endereco endereco) {
		this.endereco = endereco;
	}

	public Double getValorDiaria() {
		return valorDiaria;
	}

	public void setValorDiaria(Double valorDiaria) {
		this.valorDiaria = valorDiaria;
	}
	
}
