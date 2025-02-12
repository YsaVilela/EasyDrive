package br.com.fatec.easyDrive.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

@Entity
@Table(name = "tb_estado")
public class Estado {
	@Id
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "nome") 
	private String nome;
	
	@Column(name = "sigla") 
	private String sigla;

	public String getNome() {
		return nome;
	}

	public String getSigla() {
		return sigla;
	}

	public Long getId() {
		return id;
	}
}
