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

@Table(name = "tb_endereco")
@Entity (name = "Endereco")
public class Endereco {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "cep") 
	private String cep;
	
	@Column(name = "logradouro") 
	private String logradouro;
	
	@Column(name = "numero") 
	private String numero;
	
	@Column(name = "complemento") 
	private String complemento;
	
	@Column(name = "bairro")
    private String bairro;

	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_cidade")
	protected Cidade cidade;

	public Long getId() {
		return id;
	}

	public String getCep() {
		return cep;
	}

	public void setCep(String cep) {
		this.cep = cep;
	}

	public String getLogradouro() {
		return logradouro;
	}

	public void setLogradouro(String logradouro) {
		this.logradouro = logradouro;
	}

	public String getNumero() {
		return numero;
	}

	public void setNumero(String numero) {
		this.numero = numero;
	}

	public String getComplemento() {
		return complemento;
	}

	public void setComplemento(String complemento) {
		this.complemento = complemento;
	}

	public String getBairro() {
		return bairro;
	}

	public void setBairro(String bairro) {
		this.bairro = bairro;
	}

	public Cidade getCidade() {
		return cidade;
	}

	public void setCidade(Cidade cidade) {
		this.cidade = cidade;
	}

}
