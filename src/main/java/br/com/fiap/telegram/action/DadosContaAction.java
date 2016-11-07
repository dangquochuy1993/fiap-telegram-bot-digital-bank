package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.DadosBasicoComDependentesPrinter;

public class DadosContaAction extends AbstractExtratoAction {

	@Override
	protected ContaPrinter printer() {
		return new DadosBasicoComDependentesPrinter();
	}

}
