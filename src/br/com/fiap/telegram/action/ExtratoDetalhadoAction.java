package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoDetalhadoPrinter;

/**
 * Action cuida da execução de um extrato detalhado. O ExtratoDetalhadoPrinter é usado para essa finalidade.
 * @author diego
 *
 */
public class ExtratoDetalhadoAction extends AbstractExtratoAction {
	
	@Override
	protected ContaPrinter printer() {
		return new ExtratoDetalhadoPrinter();
	}

	
}
