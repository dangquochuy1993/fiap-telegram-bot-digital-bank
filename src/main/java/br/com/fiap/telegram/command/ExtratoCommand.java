package br.com.fiap.telegram.command;

import br.com.fiap.telegram.action.AbstractExtratoAction;
import br.com.fiap.telegram.action.ExtratoAction;
import br.com.fiap.telegram.model.Taxas;

public class ExtratoCommand extends AbstractExtratoCommand {

	public ExtratoCommand() {
		super("/extrato", "Extrato simples da conta. Taxa de uso " + Taxas.EXTRATO.getValor());
	}

	@Override
	protected AbstractExtratoAction action() {
		return new ExtratoAction();
	}
}
