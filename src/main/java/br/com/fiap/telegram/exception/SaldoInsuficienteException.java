package br.com.fiap.telegram.exception;

public class SaldoInsuficienteException extends RuntimeException {

	public SaldoInsuficienteException() {
		super("Saldo de sua conta � insuficiente para opera��o.");
	}
}
