package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoTarifasPrinter;

/**
 * Realiza extrato das tarifas de serviço utilizado na conta.
 * @author diego
 *
 */
public class ExtratoTarifasAction extends AbstractExtratoAction {
	
	@Override
	protected ContaPrinter printer() {
		return new ExtratoTarifasPrinter();
	}

	
}
