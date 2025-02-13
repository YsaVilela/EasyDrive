package br.com.fatec.easyDrive.enumerator;

public enum StatusEnum {
	
	SUSPENSO("Suspenso"), 
	ATIVO("Ativo"),
	EM_ANALISE("Em Análise"),
	CANCELADO("Cancelado"),
	FINALIZADO("Finalizado"),
	CADASTRADO("Cadastrado"),
	EM_ANDAMENTO("Em andamento"),
	ENCERRADO("Encerrado"),
	AGUARDANDO_PAGAMENTO("Aguardando Pagamento"),
	APROVADO("Aprovado");

	private String descricao;

	private StatusEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}

}
