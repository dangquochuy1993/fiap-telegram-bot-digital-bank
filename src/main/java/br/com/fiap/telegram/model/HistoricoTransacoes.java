package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

public class HistoricoTransacoes implements Serializable {
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
			sb.append(
				"\n----------------------------\n" +	
				"\nData/Hora: " + transacao.getDataHora() +
				"\nDescrição: " + transacao.getDescricao() +
				"\nValor: " + transacao.getValor() +
				"\nSaldo: " + transacao.getSaldo()				
			);
		}
		
		return sb.toString();
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
}
