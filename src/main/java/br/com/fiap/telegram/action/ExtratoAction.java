package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.ContaPrinter;
import br.com.fiap.telegram.printer.ExtratoDetalhadoPrinter;
import br.com.fiap.telegram.printer.ExtratoPrinter;

public class ExtratoAction extends AbstractExtratoAction {

	@Override
	protected ContaPrinter printer() {
		return new ExtratoPrinter();
	}	

}
