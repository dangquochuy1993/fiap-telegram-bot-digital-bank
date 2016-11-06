package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.model.HistoricoTransacoes;

public class ExtratoAction extends AbstractAction {

	@Override
	protected String execute(String router) {
		Conta conta = session.get(CONTA, Conta.class);
		HistoricoTransacoes historico = conta.extrato();
		bot.execute(new SendMessage(chatId, historico.toString()));
		
		session.put(CONTA, conta);
		return null;
	}

}
