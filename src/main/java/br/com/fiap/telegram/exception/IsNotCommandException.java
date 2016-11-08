package br.com.fiap.telegram.exception;

public class IsNotCommandException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IsNotCommandException(String texto) {
		super(texto);
	}

}
