package br.com.fatec.easyDrive.enumerator;

public enum CargoEnum {
	ATENDENTE ("Atendente"),
	MANOBRISTA ("Manobrista"),
	GERENTE ("Gerente"),
	ADMINISTRADOR ("Administrador");
	
	private String descricao;

	private CargoEnum(String descricao) {
		this.descricao = descricao;
	}

	public String getDescricao() {
		return this.descricao;
	}
}
