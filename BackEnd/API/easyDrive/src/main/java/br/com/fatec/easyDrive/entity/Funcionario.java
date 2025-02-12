package br.com.fatec.easyDrive.entity;

import br.com.fatec.easyDrive.enumerator.CargoEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Table(name = "tb_funcionario")
@Entity (name = "Funcionario")
public class Funcionario {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "cargo") 
	private CargoEnum cargo;
	
	@Column(name = "status") 
	private StatusEnum status;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_pessoa")
	protected Pessoa pessoa;

	public CargoEnum getCargo() {
		return cargo;
	}

	public void setCargo(CargoEnum cargo) {
		this.cargo = cargo;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Long getId() {
		return id;
	}
	
}
