package br.com.fiap.telegram.command;

import br.com.fiap.telegram.action.AbstractExtratoAction;
import br.com.fiap.telegram.action.DadosContaAction;

/**
 * Exibir dados básico da conta
 * @author Diego.Saouda
 *
 */
public class DadosContaCommand extends AbstractExtratoCommand {

	public DadosContaCommand() {
		super("/dadosconta", "Exibir dados da conta");
	}

	@Override
	protected AbstractExtratoAction action() {
		return new DadosContaAction();
	}

}
