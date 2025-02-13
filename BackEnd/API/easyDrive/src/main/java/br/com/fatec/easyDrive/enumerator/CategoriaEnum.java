package br.com.fatec.easyDrive.enumerator;

public enum CategoriaEnum {
	ECONOMICO("Econômico"),
	INTEMEDIARIO("Intermediário"),
	EXECUTIVO("Executivo"),
	SUV("SUV"),
	MINIVAN("Minivan"),
	UTILITARIO("Utilitário");
	
	private String descricao;

	private CategoriaEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
