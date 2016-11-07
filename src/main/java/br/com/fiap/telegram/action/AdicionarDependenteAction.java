package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;

public class AdicionarDependenteAction extends AbstractAction {


	@Override
	protected String execute(String router) {
		
		Conta conta = session.get(CONTA, Conta.class);
		Cliente dependente = new Cliente(message.text());
		conta.adicionarDepentente(dependente);
		
		bot.execute(new SendMessage(chatId, "Novo depentente ("+ dependente.getNome() +") adicionado com Sucesso"));
		
		return null;
	}

}
