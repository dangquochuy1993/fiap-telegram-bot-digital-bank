package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao implements Serializable {

	private LocalDateTime dataHora;
	private TipoTransacao tipo;
	private BigDecimal saldoConta;
	private BigDecimal valorTransacao;

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
