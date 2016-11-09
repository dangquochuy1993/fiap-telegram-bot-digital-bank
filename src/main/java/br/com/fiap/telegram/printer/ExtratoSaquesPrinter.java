package br.com.fiap.telegram.printer;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.model.HistoricoTransacoes;
import br.com.fiap.telegram.model.TipoTransacao;
import br.com.fiap.telegram.util.Helpers;

public class ExtratoSaquesPrinter implements ContaPrinter {

	@Override
	public String imprimir(Conta conta) {
		HistoricoTransacoes historico = conta.extrato();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Extrato de Saque: " + Helpers.formatarDataHora());
		
		double total = historico
			.getTransacoes()
			.stream()
			.filter(t -> t.getTipo().equals(TipoTransacao.SAQUE))
			.mapToDouble(t -> {
				sb.append("\nR$ " + t.getValor() + " em " + Helpers.formatarData(t.getDataHora()));	
				
				return t.getValor().doubleValue();
			}).sum();
		
		
		sb.append(
			"\n----------------------------\n" +
			"Total transações: " + total 
		);
		
		return sb.toString();
	}
}
