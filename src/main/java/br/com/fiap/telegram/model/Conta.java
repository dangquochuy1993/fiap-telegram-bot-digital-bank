package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.Collections;
import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import br.com.fiap.telegram.exception.SaldoInsuficienteException;

public class Conta implements Serializable {
	private int numero;
	private LocalDateTime abertura;
	private Cliente titular;
	private Set<Cliente> dependentes = new HashSet<>();
	private HistoricoTransacoes transacoes = new HistoricoTransacoes();
	
	private BigDecimal saldo;
	
	public Conta(Cliente titular, BigDecimal valorInicial) {
		numero = rand(10000000,99999999);
		abertura = LocalDateTime.now();
		
		this.titular = titular;
		saldo = valorInicial;
		
		transacoes.adicionar("Abertura conta", valorInicial, valorInicial);
	}
	
	public Conta depositar(BigDecimal valor) {
		saldo = saldo.add(valor);
		
		transacoes.adicionar("Depósito", valor, saldo);
		return this;
	}
	
	public BigDecimal saque(BigDecimal valor) {
		BigDecimal valorComTaxa = valor.add(Taxas.SAQUE.getValor());
		
		if (saldo.compareTo(valorComTaxa) < 0) {
			throw new SaldoInsuficienteException();
		}
				
		saldo = saldo.subtract(valorComTaxa);
		
		transacoes.adicionar("Saque", valor, saldo);
		
		return valor;
	}
	
	public HistoricoTransacoes extrato() {
		
		if (saldo.compareTo(Taxas.EXTRATO.getValor()) < 0) {
			throw new SaldoInsuficienteException();
		}
		
		saldo = saldo.subtract(Taxas.EXTRATO.getValor());
		transacoes.adicionar("extrato", Taxas.EXTRATO.getValor(), saldo);
		
		return transacoes;
	}
	
	public Conta adicionarDepentente(Cliente cliente) {
		transacoes.adicionar("adicionou depentende " + cliente.getNome());
		
		dependentes.add(cliente);
		return this;
	}
	
	public Conta removerDependente(Cliente cliente) {
		transacoes.adicionar("removeu depentende " + cliente.getNome());
		
		dependentes.remove(cliente);
		return this;
	}
	
	public int getNumero() {
		return numero;
	}

	public LocalDateTime getAbertura() {
		return abertura;
	}
	
	public String aberturaFormatada() {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm");
		return abertura.format(formatter);
	}

	public Cliente getTitular() {
		return titular;
	}

	public Set<Cliente> getDependentes() {
		return Collections.unmodifiableSet(dependentes);
	}

	private int rand(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", abertura=" + abertura + ", titular=" + titular
				+ ", dependentes=" + dependentes + ", saldo=" + saldo + "]";
	}
}
