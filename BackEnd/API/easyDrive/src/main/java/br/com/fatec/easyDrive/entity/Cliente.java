package br.com.fatec.easyDrive.entity;

import java.time.LocalDate;

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
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Table(name = "tb_cliente")
@Entity (name = "Cliente")
@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
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
	private LocalDate validadeCNH;
	
	@Column(name = "status") 
	private StatusEnum status;
	
	@Column(name = "data_cadastro") 
	private LocalDate dataCadastro;
	
	@ManyToOne(cascade = CascadeType.REMOVE)
	@JoinColumn(name = "fk_pessoa")
	protected Pessoa pessoa;
	
}
