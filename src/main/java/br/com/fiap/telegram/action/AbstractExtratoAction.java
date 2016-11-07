package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.ContaPrinter;

abstract public class AbstractExtratoAction extends AbstractAction {

	@Override
	protected String execute(String router) {
		Conta conta = session.get(CONTA, Conta.class);
		
		bot.execute(new SendMessage(chatId, printer().imprimir(conta)));
		
		session.put(CONTA, conta);
		return null;
	}

	protected abstract ContaPrinter printer();
}