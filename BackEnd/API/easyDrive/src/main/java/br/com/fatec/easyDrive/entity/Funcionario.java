package br.com.fatec.easyDrive.entity;

import br.com.fatec.easyDrive.enumerator.CargoEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;

import java.time.LocalDate;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tb_funcionario")
@Entity (name = "Funcionario")
@Getter
@Setter
public class Funcionario {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "cargo") 
	private CargoEnum cargo;
	
	@Column(name = "data_cadastro") 
	private LocalDate dataCadastro;
	
	@Column(name = "status") 
	private StatusEnum status;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_pessoa")
	protected Pessoa pessoa;
}
