package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoSaquesPrinter;

public class ExtratoSaquesAction extends AbstractExtratoAction {

	@Override
	protected ContaPrinter printer() {
		return new ExtratoSaquesPrinter();
	}


}
