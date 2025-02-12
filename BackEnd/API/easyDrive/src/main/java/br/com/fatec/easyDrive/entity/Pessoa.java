package br.com.fatec.easyDrive.entity;

import java.time.LocalDate;
import java.util.Date;

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

@Table(name = "tb_pessoa")
@Entity (name = "Pessoa")
@Getter 
@Setter
public class  Pessoa {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome") 
	private String nome;
	
	@Column(name = "cpf", unique = true) 
	private String cpf;
	
	@Column(name = "data_nascimento") 
	private Date dataDeNascimento;
	
	@Column(name = "telefone") 
	private String telefone;
	
	@Column(name = "email", unique = true) 
	private String email;
	
	@Column(name = "data_cadastro") 
	private LocalDate dataCadastro;
	
	@OneToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_endereco")
	protected Endereco endereco;

}
