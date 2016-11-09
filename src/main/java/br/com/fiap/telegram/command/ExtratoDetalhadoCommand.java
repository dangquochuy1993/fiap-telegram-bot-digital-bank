package br.com.fiap.telegram.command;

import br.com.fiap.telegram.action.AbstractExtratoAction;
import br.com.fiap.telegram.action.ExtratoDetalhadoAction;
import br.com.fiap.telegram.model.Taxas;

public class ExtratoDetalhadoCommand extends AbstractExtratoCommand {

	public ExtratoDetalhadoCommand() {
		super("/extratodetalhado", "Extrato detalhado de todas as movimentações da conta. Taxa de uso " + Taxas.EXTRATO.getValor());
	}

	@Override
	protected AbstractExtratoAction action() {
		return new ExtratoDetalhadoAction();
	}

}
