package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoDetalhadoPrinter;

public class ExtratoDetalhadoAction extends AbstractExtratoAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected ContaPrinter printer() {
		return new ExtratoDetalhadoPrinter();
	}

	
}
