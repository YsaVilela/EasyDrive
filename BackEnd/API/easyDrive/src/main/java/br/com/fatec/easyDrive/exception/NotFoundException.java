package br.com.fatec.easyDrive.exception;

public class NotFoundException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public NotFoundException(String mensage) {
        super(mensage);
    }
}
