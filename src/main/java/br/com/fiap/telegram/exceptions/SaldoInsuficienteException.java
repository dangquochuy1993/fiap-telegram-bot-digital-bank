package br.com.fiap.telegram.exceptions;

public class SaldoInsuficienteException extends RuntimeException {

	public SaldoInsuficienteException() {
		super("Saldo de sua conta é insuficiente para operação.");
	}
}
