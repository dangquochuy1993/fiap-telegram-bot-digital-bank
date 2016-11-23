package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoPrinter;

/**
 * Extrato simplificado de uma conta.
 * @author diego
 *
 */
public class ExtratoAction extends AbstractExtratoAction {
	
	@Override
	protected ContaPrinter printer() {
		return new ExtratoPrinter();
	}	

}
