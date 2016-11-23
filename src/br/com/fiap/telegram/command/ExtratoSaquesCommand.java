package br.com.fiap.telegram.command;

import br.com.fiap.telegram.action.AbstractExtratoAction;
import br.com.fiap.telegram.action.ExtratoSaquesAction;

/**
 * Extrato de saques efetuados na conta
 * @author Diego.Saouda
 *
 */
public class ExtratoSaquesCommand extends AbstractExtratoCommand {

	public ExtratoSaquesCommand() {
		super("/extratosaques", "Extrato de operações de saque");
	}

	@Override
	protected AbstractExtratoAction action() {
		return new ExtratoSaquesAction();
	}
}
