package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * Transação de uma conta. Essa transação armazena data e hora da transação, o tipo, o valor e o saldo da conta após a transação.
 * @author Diego.Saouda
 *
 */
public class Transacao implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private LocalDateTime dataHora;
	private TipoTransacao tipo;
	private BigDecimal saldoConta;
	private BigDecimal valorTransacao;

	/**
	 * Gerar uma nova transação
	 * @param dataHora data/hora da transação (valor recebido por fora para permitir um mock se necessário)
	 * @param tipo Tipo da transação
	 * @param valorTransacao valor da transação
	 * @param saldoConta após transação
	 */
	public Transacao(LocalDateTime dataHora, TipoTransacao tipo, BigDecimal valorTransacao, BigDecimal saldoConta) {
		this.dataHora = dataHora;
		this.tipo = tipo;
		this.valorTransacao = valorTransacao;
		this.saldoConta = saldoConta;		
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public TipoTransacao getTipo() {
		return tipo;
	}

	public BigDecimal getSaldo() {
		return saldoConta;
	}

	public BigDecimal getValor() {
		return valorTransacao;
	}

	@Override
	public String toString() {
		return "Transacao [dataHora=" + dataHora + ", tipo=" + tipo.descricao() + ", saldoConta=" + saldoConta
				+ ", valorTransacao=" + valorTransacao + "]";
	}
}
