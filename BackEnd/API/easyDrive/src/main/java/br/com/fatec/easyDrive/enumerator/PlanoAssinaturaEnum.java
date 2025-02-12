package br.com.fatec.easyDrive.enumerator;

public enum PlanoAssinaturaEnum {
	ECONOMICO("Econômico", 0L, 0), 
	EXECUTIVO("Executivo", 500L, 5),
	PREMIUM("Premium", 2500L, 15),
	ELITE("Elite", 10000L, 30);

	private String descricao;
	private Long pontuacaoMinima;
	private Integer desconto;

	private PlanoAssinaturaEnum(String descricao, Long pontuacaoMinima, Integer desconto) {
		this.descricao = descricao;
		this.pontuacaoMinima = pontuacaoMinima;
		this.desconto = desconto;
	}

	public String getDescricao() {
		return this.descricao;
	}

	public Long getPontuacaoMinima() {
		return pontuacaoMinima;
	}

	public Integer getDesconto() {
		return desconto;
	}

}
