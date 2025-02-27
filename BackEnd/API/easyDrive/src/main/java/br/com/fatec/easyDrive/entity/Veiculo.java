package br.com.fatec.easyDrive.entity;

import br.com.fatec.easyDrive.enumerator.CategoriaEnum;
import br.com.fatec.easyDrive.enumerator.StatusEnum;
import br.com.fatec.easyDrive.enumerator.TipoCombustivelEnum;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Table(name = "tb_veiculo")
@Entity (name = "Veiculo")
@Getter
@Setter
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
	private CategoriaEnum categoria;
	
	@Column(name = "tipo_combustivel") 
	private TipoCombustivelEnum tipoCombustivel;
	
	@Column(name = "cor") 
	private String cor;
	
	@Column(name = "valor_diaria")
	private Double valorDiaria;
	
    @Enumerated(EnumType.STRING)
	@Column(name = "status") 
	private StatusEnum status;
	
}
