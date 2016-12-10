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

/**
 * Classe responsável por controlar o empréstimo de uma conta
 * @author Diego.Saouda
 *
 */
public class Emprestimo implements Serializable {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Fator máximo de empréstimo. Esse valor é múltiplicado ao saldo da conta. Se o valor do empréstimo for esse número x maior que o saldo em conta, não será possível realizar o empréstimo.
	 */
	public static final int X_SALDO_EMPRESTIMO_MAXIMO = 40;
	
	/**
	 * taxa de juros por mês para o empréstimo
	 */
	public static final float JUROS_MES = 0.05f;

	/**
	 * regex para extrair o período do empréstimo
	 */
	private final String pattern = "^(\\d*)\\s(mes|mês|meses|anos|ano)$";
	private final Pattern regex = Pattern.compile(pattern);

	/**
	 * valor do empréstimo
	 */
	private BigDecimal valor;
	
	/**
	 * prazo para pagamento
	 */
	private LocalDate prazo;
	
	/**
	 * juros a serem pagos por mês
	 */
	private BigDecimal jurosMes;
	
	/**
	 * valor a ser pago por mês
	 */
	private BigDecimal valorMes;
	
	/**
	 * valor total a ser pago no mês (jurosMes + valorMes)
	 */
	private BigDecimal valorTotalMes;
	
	/**
	 * valor total de a ser pago pelo empréstimo
	 */
	private BigDecimal valorTotal;
	
	/**
	 * Iniciar um emprétimo. O empréstimo é imutável.
	 * @param conta a ser aplicado o empréstimo
	 * @param valor solicitação do empréstimo
	 * @param prazo para pagamento
	 * @throws PrazoPagamentoException se o prazo do pagamento for maior que o permitido
	 * @throws SaldoInsuficienteException se saldo insuficiente
	 */
	public Emprestimo(Conta conta, BigDecimal valor, String prazo) {
		this.valor = valor;
		this.prazo = prazoPagamento(prazo);
		calcular(conta);
	}
	
	/**
	 * Calcular o empréstimo
	 * @param conta
	 * @throws SaldoInsuficienteException se não for possível realizar o empréstimo
	 */
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
	
	/**
	 * transformar o texto informado pelo usuário em um objeto do tipo LocalDate
	 * @param prazo Texto com prazo para pagamento. ex: 1 ano ou 2 meses 
	 * @return
	 */
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
