package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import br.com.fiap.telegram.exception.SaldoInsuficienteException;
import br.com.fiap.telegram.util.Helpers;

public class Conta implements Serializable {
	private int numero;
	private LocalDateTime abertura;
	private Cliente titular;
	private Set<Cliente> dependentes = new HashSet<>();
	private HistoricoTransacoes transacoes = new HistoricoTransacoes();
	
	private BigDecimal saldo;
	
	public Conta(Cliente titular, BigDecimal valorInicial) {
		numero = Helpers.geradorNumero(10000000,99999999);
		abertura = LocalDateTime.now();
		
		this.titular = titular;
		saldo = valorInicial;
		
		transacoes.adicionar("Abertura conta", valorInicial, valorInicial);
	}
	
	public Conta depositar(BigDecimal valor) {
		saldo = saldo.add(valor);
		
		transacoes.adicionar("Dep�sito", valor, saldo);
		return this;
	}
	
	public BigDecimal saque(BigDecimal valor) {
		valor = valor.negate();		
		exceptionSeNaoTiverSaldoSuficiente(valor);
				
		saldo = saldo.add(Taxas.SAQUE.getValor());		
		transacoes.adicionar("Taxa saque", Taxas.SAQUE.getValor(), saldo);
		
		saldo = saldo.add(valor);
		transacoes.adicionar("Saque", valor, saldo);
		
		return valor;
	}

	private void exceptionSeNaoTiverSaldoSuficiente(BigDecimal valor) {
		if (saldo.add(valor).add(Taxas.SAQUE.getValor()).intValue() < 0) {
			throw new SaldoInsuficienteException();
		}
	}
	
	public HistoricoTransacoes extrato() {
		
		if (saldo.compareTo(Taxas.EXTRATO.getValor()) < 0) {
			throw new SaldoInsuficienteException();
		}
		
		saldo = saldo.add(Taxas.EXTRATO.getValor());
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
	
	public Cliente getTitular() {
		return titular;
	}

	public Set<Cliente> getDependentes() {
		return Collections.unmodifiableSet(dependentes);
	}
	
	public BigDecimal getSaldo() {
		return saldo;
	}
	
	public String exibirDadosBasico() {		
		return "\nTitular: " + titular.getNome() +
				"\nNumero: " + numero +
				"\nAbertura: " + Helpers.dataHoraFormatado(abertura) +
				"\nSaldo: " + saldo;		
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", abertura=" + abertura + ", titular=" + titular
				+ ", dependentes=" + dependentes + ", saldo=" + saldo + "]";
	}
}
