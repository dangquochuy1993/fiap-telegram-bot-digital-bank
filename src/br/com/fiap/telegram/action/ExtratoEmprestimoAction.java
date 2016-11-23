package br.com.fiap.telegram.action;

import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.EmprestimoPrinter;

/**
 * Realiza o extrado de um empréstimo
 * @author diego
 *
 */
public class ExtratoEmprestimoAction extends AbstractExtratoAction {
	
	@Override
	protected ContaPrinter printer() {
		return new EmprestimoPrinter();
	}


}
