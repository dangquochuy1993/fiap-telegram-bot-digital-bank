package br.com.fiap.telegram.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Random;

public class Conta {
	private int agencia;
	private int numero;
	private LocalDateTime abertura;
	private Cliente titular;
	private List<Cliente> dependentes;
	
	private BigDecimal saldo;
	
	public Conta(Cliente titular, BigDecimal valorInicial) {
		agencia = rand(1000, 9999);
		numero = rand(10000000,99999999);
		abertura = LocalDateTime.now();
		
		this.titular = titular;
		saldo = valorInicial;
	}
	
	public Conta depositar(BigDecimal valor) {
		saldo.add(valor);
		return this;
	}
	
	public BigDecimal saque(BigDecimal valor) {
		BigDecimal valorComTaxa = valor.add(Taxas.SAQUE.getValor());
		valorComTaxa.compareTo(saldo);
				
		saldo.subtract(valorComTaxa);
		
		return valor;
	}
	
	public BigDecimal extrato() {
		saldo.subtract(Taxas.EXTRATO.getValor());		
		return saldo;
	}
	
	public Conta addDepentente(Cliente cliente) {
		dependentes.add(cliente);
		return this;
	}
	
	public Conta removerDependente(Cliente cliente) {
		dependentes.remove(cliente);
		return this;
	}
	
	public int getAgencia() {
		return agencia;
	}

	public int getNumero() {
		return numero;
	}

	public LocalDateTime getAbertura() {
		return abertura;
	}

	public Cliente getTitular() {
		return titular;
	}

	public List<Cliente> getDependentes() {
		return dependentes;
	}

	private int rand(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Override
	public String toString() {
		return "Conta [agencia=" + agencia + ", numero=" + numero + ", abertura=" + abertura + ", titular=" + titular
				+ ", dependentes=" + dependentes + ", saldo=" + saldo + "]";
	}
}
