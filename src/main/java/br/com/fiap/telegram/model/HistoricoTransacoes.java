package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import br.com.fiap.telegram.util.Helpers;

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
		BigDecimal valor = new BigDecimal(0);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Extrato em " + Helpers.dataHoraFormatado());
		
		for (Transacao transacao : historico) {
			sb.append(
				"\n----------------------------" +	
				"\nData/Hora: " + Helpers.dataHoraFormatado(transacao.getDataHora()) +
				"\nDescrição: " + transacao.getDescricao() +
				"\nValor: " + transacao.getValor() +
				"\nSaldo: " + transacao.getSaldo()				
			);
			
			valor = valor.add(transacao.getValor());
		}
		
		sb.append(
			"\n----------------------------\n" +
			"Total transações: " + valor 
		);
		
		return sb.toString();
	}
	
	public static String padRight(String s, int n) {
	     return String.format("%1$-" + n + "s", s);  
	}
}
