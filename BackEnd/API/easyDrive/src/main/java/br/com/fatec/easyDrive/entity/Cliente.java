package br.com.fatec.easyDrive.entity;

import java.util.Date;

import br.com.fatec.easyDrive.enumerator.PlanoAssinaturaEnum;
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

@Table(name = "tb_cliente")
@Entity (name = "Cliente")
public class Cliente {
	@Id  @GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column(name = "id") 
	private Long id;
	
	@Column(name = "pontuacao") 
	private Long pontuacao;
	
	@Column(name = "plano_assinatura") 
	private PlanoAssinaturaEnum planoAssinatura;
	
	@Column(name = "numero_cnh") 
	private String numeroCNH;
	
	@Column(name = "validade_cnh") 
	private Date validadeCNH;
	
	@Column(name = "status") 
	private StatusEnum status;
	
	@ManyToOne(cascade = CascadeType.PERSIST)
	@JoinColumn(name = "fk_pessoa")
	protected Pessoa pessoa;

	public Long getId() {
		return id;
	}

	public Long getPontuacao() {
		return pontuacao;
	}

	public void setPontuacao(Long pontuacao) {
		this.pontuacao = pontuacao;
	}

	public PlanoAssinaturaEnum getPlanoAssinatura() {
		return planoAssinatura;
	}

	public void setPlanoAssinatura(PlanoAssinaturaEnum planoAssinatura) {
		this.planoAssinatura = planoAssinatura;
	}

	public StatusEnum getStatus() {
		return status;
	}

	public void setStatus(StatusEnum status) {
		this.status = status;
	}

	public Pessoa getPessoa() {
		return pessoa;
	}

	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	
	public String getNumeroCNH() {
		return numeroCNH;
	}

	public void setNumeroCNH(String numeroCNH) {
		this.numeroCNH = numeroCNH;
	}

	public Date getValidadeCNH() {
		return validadeCNH;
	}

	public void setValidadeCNH(Date validadeCNH) {
		this.validadeCNH = validadeCNH;
	}
	
}
