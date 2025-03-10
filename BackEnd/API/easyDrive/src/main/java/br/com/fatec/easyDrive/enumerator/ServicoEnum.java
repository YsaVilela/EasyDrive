package br.com.fatec.easyDrive.enumerator;

public enum ServicoEnum {
	LIMPEZA("Limpeza Completa", 5), 
	SEGURO("Seguro Completo contra Acidentes", 10),
	ASSISTENCIA_24H("Assistência 24h", 8),
	CADEIRA_INFANTIL("Cadeira para Criança", 2),
	TANQUE_CHEIO("Tanque Cheio na Retirada", 10);
	
	private String descricao;
	private Integer valor;
	
	private ServicoEnum(String descricao, Integer desconto) {
		this.descricao = descricao;
		this.valor = desconto;
	}

	public String getDescricao() {
		return descricao;
	}

	public Integer getValor() {
		return valor;
	}
	
}
