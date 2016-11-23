package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.DadosBasicoComDependentesPrinter;

/**
 * Action responsável por exibir dados da conta.
 * @author diego
 *
 */
public class DadosContaAction extends AbstractExtratoAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected ContaPrinter printer() {
		return new DadosBasicoComDependentesPrinter();
	}

}
