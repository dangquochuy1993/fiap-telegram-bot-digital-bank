package br.com.fiap.telegram.command;

import br.com.fiap.telegram.action.AbstractExtratoAction;
import br.com.fiap.telegram.action.ExtratoEmprestimoAction;

public class ExtratoEmprestimoCommand extends AbstractExtratoCommand {

	public ExtratoEmprestimoCommand() {
		super("/extratoemprestimo", "Visualizar informações sobre o empréstimo.");
	}

	@Override
	protected AbstractExtratoAction action() {
		return new ExtratoEmprestimoAction();
	}
}
