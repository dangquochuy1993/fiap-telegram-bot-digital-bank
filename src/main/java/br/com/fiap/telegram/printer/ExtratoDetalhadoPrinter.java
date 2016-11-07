package br.com.fiap.telegram.printer;

import java.math.BigDecimal;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.model.HistoricoTransacoes;
import br.com.fiap.telegram.model.Transacao;
import br.com.fiap.telegram.util.Helpers;

public class ExtratoDetalhadoPrinter implements ContaPrinter {

	@Override
	public String imprimir(Conta conta) {
		HistoricoTransacoes historico = conta.extrato();
		BigDecimal valor = new BigDecimal(0);
		
		StringBuilder sb = new StringBuilder();
		sb.append("Extrato em " + Helpers.formatarDataHora());
		
		for (Transacao transacao : historico.getTransacoes()) {
			sb.append(
				"\n----------------------------" +	
				"\nData/Hora: " + Helpers.formatarDataHora(transacao.getDataHora()) +
				"\nDescrição: " + transacao.getTipo().descricao() +
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
}
