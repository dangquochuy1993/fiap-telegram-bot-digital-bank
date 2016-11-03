package br.com.fiap.telegram.model;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoricoTransacoes {
	private List<Transacao> historico = new ArrayList<>();
	
	public HistoricoTransacoes adicionar(String descricao, BigDecimal valor, BigDecimal saldo) {
		historico.add(new Transacao(LocalDateTime.now(), descricao, valor, saldo));
		return this;
	}

	public HistoricoTransacoes adicionar(String descricao) {
		adicionar(descricao, new BigDecimal(0), new BigDecimal(0));
		return this;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for (Transacao transacao : historico) {
			sb.append(transacao);
		}
		
		return sb.toString();
	}
}
