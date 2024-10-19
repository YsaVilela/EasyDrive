package br.com.fatec.easyDrive.Entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "tb_cliente")
@Entity (name = "Cliente")
public class Cliente {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "numero_cnh") 
	private String numeroCNH;
	
	@Column(name = "validade_cnh") 
	private String validadeCNH;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_pessoa")
	protected Pessoa pessoa;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNumeroCNH() {
		return numeroCNH;
	}

	public void setNumeroCNH(String numeroCNH) {
		this.numeroCNH = numeroCNH;
	}

	public String getValidadeCNH() {
		return validadeCNH;
	}

	public void setValidadeCNH(String validadeCNH) {
		this.validadeCNH = validadeCNH;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
}
