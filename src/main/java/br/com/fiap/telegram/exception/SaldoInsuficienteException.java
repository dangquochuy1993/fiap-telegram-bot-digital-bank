package br.com.fiap.telegram.exception;

public class SaldoInsuficienteException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public SaldoInsuficienteException() {
		super("Saldo de sua conta é insuficiente para operação.");
	}
}
