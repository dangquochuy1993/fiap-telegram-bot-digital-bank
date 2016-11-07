package br.com.fiap.telegram.command;

import br.com.fiap.telegram.action.AbstractExtratoAction;
import br.com.fiap.telegram.action.ExtratoSaquesAction;

public class ExtratoSaquesCommand extends AbstractExtratoCommand {

	public ExtratoSaquesCommand() {
		super("/extratosaques", "Extrato de opera��es de saque");
	}

	@Override
	protected AbstractExtratoAction action() {
		return new ExtratoSaquesAction();
	}
}
