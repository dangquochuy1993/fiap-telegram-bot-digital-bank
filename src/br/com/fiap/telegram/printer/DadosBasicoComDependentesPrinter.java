package br.com.fiap.telegram.printer;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.util.Helpers;

/**
 * Imprimir dados basico de uma conta exibindo dependentes 
 * @author Diego.Saouda
 *
 */
public class DadosBasicoComDependentesPrinter implements ContaPrinter {

	@Override
	public String imprimir(Conta conta) {
		StringBuilder sb = new StringBuilder("\nConta: " + conta.getNumero());
		sb.append("\nAbertura: " + Helpers.formatarDataHora(conta.getAbertura()));
		sb.append("\nSaldo: " + conta.getSaldo());
		sb.append("\n");
		sb.append("\nTitular: " + conta.getTitular().getNome());		
		sb.append("\nDependentes");		
		
		conta
			.getDependentes()
			.forEach(d -> {
				sb.append("\n\t" + d.getNome());
			});
		
		return sb.toString();		
	}

}
