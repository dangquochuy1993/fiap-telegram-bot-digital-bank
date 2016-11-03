package br.com.fiap.telegram.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class Transacao {

	private LocalDateTime dataHora;
	private String descricao;
	private BigDecimal saldoConta;
	private BigDecimal valorTransacao;

	public Transacao(LocalDateTime dataHora, String descricao, BigDecimal valorTransacao, BigDecimal saldoConta) {
		this.dataHora = dataHora;
		this.descricao = descricao;
		this.valorTransacao = valorTransacao;
		this.saldoConta = saldoConta;		
	}

	public LocalDateTime getDataHora() {
		return dataHora;
	}

	public String getDescricao() {
		return descricao;
	}

	public BigDecimal getSaldo() {
		return saldoConta;
	}

	public BigDecimal getValor() {
		return valorTransacao;
	}

	@Override
	public String toString() {
		return "Transacao [dataHora=" + dataHora + ", descricao=" + descricao + ", saldoConta=" + saldoConta
				+ ", valorTransacao=" + valorTransacao + "]";
	}
}
