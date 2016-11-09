package br.com.fiap.telegram.printer;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.model.HistoricoTransacoes;
import br.com.fiap.telegram.model.Transacao;
import br.com.fiap.telegram.util.Helpers;

public class ExtratoPrinter implements ContaPrinter {

	@Override
	public String imprimir(Conta conta) {
		HistoricoTransacoes historico = conta.extrato();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Extrato em " + Helpers.formatarDataHora());
		
		for (Transacao transacao : historico.getTransacoes()) {
			sb.append(
				"\n----------------------------" +	
				"\nData: " + Helpers.formatarData(transacao.getDataHora()) +
				"\nDescrição: " + transacao.getTipo().descricao() +
				"\nValor: " + transacao.getValor()
			);
			
		}
		
		return sb.toString();		
	}
}
