package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoTarifasPrinter;

public class ExtratoTarifasAction extends AbstractExtratoAction {

	@Override
	protected ContaPrinter printer() {
		return new ExtratoTarifasPrinter();
	}

	
}
