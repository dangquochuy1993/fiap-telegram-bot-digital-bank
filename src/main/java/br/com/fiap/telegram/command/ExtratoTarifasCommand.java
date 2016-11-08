package br.com.fiap.telegram.command;

import br.com.fiap.telegram.action.AbstractExtratoAction;
import br.com.fiap.telegram.action.ExtratoTarifasAction;

public class ExtratoTarifasCommand extends AbstractExtratoCommand {

	public ExtratoTarifasCommand() {
		super("/extratotarifas", "Extrato de todas as tarifas cobradas pelo banco.");
	}

	@Override
	protected AbstractExtratoAction action() {
		return new ExtratoTarifasAction();
	}

}
