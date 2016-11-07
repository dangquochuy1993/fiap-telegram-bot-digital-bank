package br.com.fiap.telegram.printer;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.model.HistoricoTransacoes;
import br.com.fiap.telegram.model.TipoTransacao;
import br.com.fiap.telegram.util.Helpers;

public class ExtratoTarifasPrinter implements ContaPrinter {

	@Override
	public String imprimir(Conta conta) {
		HistoricoTransacoes historico = conta.extrato();
		
		StringBuilder sb = new StringBuilder();
		sb.append("Extrato de tarifas: " + Helpers.formatarDataHora());
		
		double total = historico
			.getTransacoes()
			.stream()
			.filter(t -> t.getTipo().equals(TipoTransacao.TAXA_SAQUE) || t.getTipo().equals(TipoTransacao.TAXA_EXTRATO))
			.mapToDouble(t -> {
				sb.append("\n-----------------------------");
				sb.append("\n" + t.getTipo().descricao());
				sb.append("\nR$ " + t.getValor() + " em " + Helpers.formatarData(t.getDataHora()));	
				
				return t.getValor().doubleValue();
			}).sum();
		
		
		sb.append(
			"\n----------------------------\n" +
			"Total transa��es: " + total 
		);
		
		return sb.toString();
	}
}
