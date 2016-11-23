package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoSaquesPrinter;

/**
 * Realiza o extrato de um saque
 * @author diego
 *
 */
public class ExtratoSaquesAction extends AbstractExtratoAction {
	
	@Override
	protected ContaPrinter printer() {
		return new ExtratoSaquesPrinter();
	}


}
