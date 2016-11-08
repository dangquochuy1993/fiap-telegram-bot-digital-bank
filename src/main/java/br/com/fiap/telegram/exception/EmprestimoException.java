package br.com.fiap.telegram.exception;

public class EmprestimoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmprestimoException(String message) {
		super(message);
	}
}
