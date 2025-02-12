package br.com.fatec.easyDrive.enumerator;

public enum StatusEnum {
	
	SUSPENSO("Suspenso"), 
	ATIVO("Ativo"),
	EM_ANALISE("Em Análise"),
	CANCELADO("Cancelado"),
	FINALIZADO("Finalizado");

	private String descricao;

	private StatusEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
