package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoDetalhadoPrinter;

public class ExtratoDetalhadoAction extends AbstractExtratoAction {

	@Override
	protected ContaPrinter printer() {
		return new ExtratoDetalhadoPrinter();
	}

	
}
