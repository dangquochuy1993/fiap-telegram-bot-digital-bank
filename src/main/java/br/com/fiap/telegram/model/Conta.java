package br.com.fiap.telegram.model;

import static br.com.fiap.telegram.model.TipoTransacao.ABERTURA_CONTA;
import static br.com.fiap.telegram.model.TipoTransacao.EMPRESTIMO;
import static br.com.fiap.telegram.model.TipoTransacao.ADICIONADO_DEPENDENTE;
import static br.com.fiap.telegram.model.TipoTransacao.DEPOSITO;
import static br.com.fiap.telegram.model.TipoTransacao.REMOVIDO_DEPENDENTE;
import static br.com.fiap.telegram.model.TipoTransacao.SAQUE;
import static br.com.fiap.telegram.model.TipoTransacao.TAXA_EXTRATO;
import static br.com.fiap.telegram.model.TipoTransacao.TAXA_SAQUE;
import static br.com.fiap.telegram.model.TipoTransacao.TAXA_EMPRESTIMO;
import static br.com.fiap.telegram.model.TipoTransacao.TAXA_EMPRESTIMO_DEVOLUCAO;
import static br.com.fiap.telegram.model.TipoTransacao.TRANSFERENCIA;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import br.com.fiap.telegram.exception.EmprestimoException;
import br.com.fiap.telegram.exception.SaldoInsuficienteException;
import br.com.fiap.telegram.util.Helpers;
import br.com.fiap.telegram.util.Logger;

public class Conta implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private int numero;
	private LocalDateTime abertura;
	private Cliente titular;
	private Set<Cliente> dependentes = new HashSet<>();
	private HistoricoTransacoes transacoes = new HistoricoTransacoes();
	private Conta antiga;
	private Emprestimo emprestimo;
	
	private BigDecimal saldo;
	
	public Conta(Cliente titular, BigDecimal valorInicial) {
		numero = Helpers.geradorNumero(10000000, 99999999);
		abertura = LocalDateTime.now();
		
		this.titular = titular;
		saldo = valorInicial;
		
		transacao(ABERTURA_CONTA, valorInicial);
	}
	
	public Conta emprestimo(BigDecimal valor, String prazo) {		
		exceptionSeJaExistirUmEmprestimoAtivo();
		
		try {
			transacao(TAXA_EMPRESTIMO, Taxas.EMPRESTIMO.getValor());
			emprestimo = new Emprestimo(this, valor, prazo);
			saldo = saldo.add(valor);
			
			transacao(EMPRESTIMO, valor);
		} catch (SaldoInsuficienteException e) {
			//devolvendo a taxa de empréstimo devido a uma falha ...
			saldo = saldo.subtract(Taxas.EMPRESTIMO.getValor());
			transacao(TAXA_EMPRESTIMO_DEVOLUCAO, Taxas.EMPRESTIMO.getValor().negate());
			
			Logger.error("saldo insuficiente");
			
			//relança exception
			throw new SaldoInsuficienteException(e.getMessage());
		}
		
		return this;
	}

	private void exceptionSeJaExistirUmEmprestimoAtivo() {
		if (emprestimo != null) {
			Logger.error("outro emprestimo em andamento");
			throw new EmprestimoException("Você já possui um emprestimo em andamento e não pode realizar outro.");
		}
	}
	
	public Conta depositar(BigDecimal valor) {
		saldo = saldo.add(valor);
		
		transacao(DEPOSITO, valor);
		return this;
	}
	
	public BigDecimal saque(BigDecimal valor) {
		valor = valor.negate();		
		throwIfSaldoInsuficiente(valor);
				
		saldo = saldo.add(Taxas.SAQUE.getValor());		
		transacao(TAXA_SAQUE, Taxas.SAQUE.getValor());
		
		saldo = saldo.add(valor);
		transacao(SAQUE, valor);
		
		return valor;
	}

	private void throwIfSaldoInsuficiente(BigDecimal valor) {
		if (saldo.add(valor).add(Taxas.SAQUE.getValor()).intValue() < 0) {
			Logger.error("saldo insuficiente");
			throw new SaldoInsuficienteException();
		}
	}
	
	public HistoricoTransacoes extrato() {		
		if (saldo.compareTo(Taxas.EXTRATO.getValor()) < 0) {
			Logger.error("saldo insuficiente");
			throw new SaldoInsuficienteException();
		}
		
		saldo = saldo.add(Taxas.EXTRATO.getValor());
		transacao(TAXA_EXTRATO, Taxas.EXTRATO.getValor());		
		return transacoes;
	}
	
	/**
	 * Adicionar dependente na conta
	 * @param cliente
	 * @return
	 */
	public Conta adicionarDepentente(Cliente cliente) {
		transacao(ADICIONADO_DEPENDENTE);		
		dependentes.add(cliente);
		return this;
	}

	/**
	 * Remover dependente da conta
	 * @param cliente
	 * @return
	 */
	public boolean removerDependente(Cliente cliente) {
		transacao(REMOVIDO_DEPENDENTE);		
		return dependentes.remove(cliente);
	}
	
	
	/**
	 * Simular um processo de trag de conta (item 3. modificar conta)
	 * @return
	 */
	public Conta migrar() {
		Conta conta = new Conta(titular, saldo);		
		dependentes.forEach(conta::adicionarDepentente);		
		conta.transacao(TRANSFERENCIA);
		
		conta.antiga = this;
		conta.antiga.saldo = BigDecimal.ZERO;
		conta.antiga.transacao(TRANSFERENCIA);
		
		return conta;
	}
	
	private void transacao(TipoTransacao tipo) {
		transacoes.adicionar(tipo);
	}
	
	private void transacao(TipoTransacao tipo, BigDecimal valor) {
		transacoes.adicionar(tipo, valor, saldo);
	}
	
	/**
	 * Visualizar conta antiga
	 * @return
	 */
	public Conta antiga() {
		return antiga;
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
	
	public List<Transacao> getTransacoes() {
		return transacoes.getTransacoes();
	}
	
	public Emprestimo getEmprestimo() {
		return emprestimo;
	}

	@Override
	public String toString() {
		return "Conta [numero=" + numero + ", abertura=" + abertura + ", titular=" + titular + ", dependentes="
				+ dependentes + ", transacoes=" + transacoes + ", antiga=" + antiga + ", emprestimo=" + emprestimo
				+ ", saldo=" + saldo + "]";
	}
}
