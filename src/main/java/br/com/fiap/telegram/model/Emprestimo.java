package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import br.com.fiap.telegram.exception.PrazoPagamentoException;
import br.com.fiap.telegram.exception.SaldoInsuficienteException;

public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	public static final int X_SALDO_EMPRESTIMO_MAXIMO = 40;
	public static final float JUROS_MES = 0.05f;

	private final String pattern = "^(\\d*)\\s(mes|mês|meses|anos|ano)$";
	private final Pattern regex = Pattern.compile(pattern);

	private BigDecimal valor;
	private LocalDate prazo;
	private BigDecimal jurosMes;
	private BigDecimal valorMes;
	private BigDecimal valorTotalMes;
	private BigDecimal valorTotal;
	
	public Emprestimo(Conta conta, BigDecimal valor, String prazo) {
		this.valor = valor;
		this.prazo = prazoPagamento(prazo);
		calcular(conta);
	}
	
	private void calcular(Conta conta) {
		
		if (conta.getSaldo().multiply(new BigDecimal(X_SALDO_EMPRESTIMO_MAXIMO)).compareTo(valor) < 0) {
			throw new SaldoInsuficienteException("Você não pode solicitar um emprestimo " + X_SALDO_EMPRESTIMO_MAXIMO + "x maior que seu saldo atual");
		}
		
		BigDecimal mesesRestante = new BigDecimal(ChronoUnit.MONTHS.between(LocalDate.now(), prazo));
		
		valorMes = valor.divide(mesesRestante, RoundingMode.CEILING);		
		jurosMes = valorMes.multiply(new BigDecimal(JUROS_MES));
		valorTotalMes = valorMes.add(jurosMes);
		valorTotal = valorTotalMes.multiply(mesesRestante);
	}
	
	private LocalDate prazoPagamento(String prazo) {
		Matcher m = regex.matcher(prazo.toLowerCase());		
		exceptionSePadraoRegexNaoIdentificado(m, prazo);
		
		int numero = Integer.parseInt(m.group(1));
		String periodo = m.group(2);		
		LocalDate dataAtual = LocalDate.now();
		
		exceptionSeValorInformadoMenorOuIgualAZero(numero);
		
		if (isMes(periodo)) {			
			exceptionSeMesMaiorQue48(numero);			
			return dataAtual.plusMonths(numero); 
		}
		
		if (isAno(periodo)) {			
			exceptionSeAnoMaiorQue3(numero);			
			return dataAtual.plusYears(numero); 
		}
		
		return dataAtual;
	}

	private void exceptionSePadraoRegexNaoIdentificado(Matcher m, String padrao) {
		if (!m.matches()) {
			throw new PrazoPagamentoException("Não identifiquei o padrão " + padrao);
		}
	}

	private void exceptionSeValorInformadoMenorOuIgualAZero(int numero) {
		if (numero <= 0) {
			throw new PrazoPagamentoException("Valor informado não é válido: " + numero);
		}
	}

	private void exceptionSeMesMaiorQue48(int numero) {
		if (numero > 36) {
			throw new PrazoPagamentoException("Você não pode solicitar um emprestimo com mais de 36 meses");
		}
	}

	private void exceptionSeAnoMaiorQue3(int numero) {
		if (numero > 3) {
			throw new PrazoPagamentoException("Você não pode solicitar um emprestimo com mais de 3 anos");
		}
	}

	private boolean isAno(String periodo) {
		return periodo.equals("ano") || periodo.equals("anos");
	}

	private boolean isMes(String periodo) {
		return periodo.equals("mes") || periodo.equals("mês") || periodo.equals("meses");
	}

	public BigDecimal getValor() {
		return valor;
	}

	public LocalDate getPrazo() {
		return prazo;
	}

	public BigDecimal getJurosMes() {
		return jurosMes;
	}

	public BigDecimal getValorMes() {
		return valorMes;
	}

	public BigDecimal getValorTotalMes() {
		return valorTotalMes;
	}

	public BigDecimal getValorTotal() {
		return valorTotal;
	}

	@Override
	public String toString() {
		return "Emprestimo [valor=" + valor + ", prazo=" + prazo + ", jurosMes=" + jurosMes + ", valorMes=" + valorMes
				+ ", valorTotalMes=" + valorTotalMes + ", valorTotal=" + valorTotal + "]";
	}
}
