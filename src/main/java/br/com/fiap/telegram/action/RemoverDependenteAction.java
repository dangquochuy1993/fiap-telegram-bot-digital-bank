package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import java.util.Set;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;

public class RemoverDependenteAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private static final String ROUTER_REMOVER = "routerRemover";

	@Override
	protected String execute(String router) {
		switch(router) {
			case ROUTER_REMOVER:
				return routerRemover();
		}
		
		return routerInit();
	}

	private String routerInit() {
		Conta conta = session.get(CONTA, Conta.class);
		SendMessage send = new SendMessage(chatId, "Remover Dependente\nInforme o nome do dependente, se preferir escolha uma opção abaixo.");
		Set<Cliente> dependentes = conta.getDependentes();
		
		KeyboardButton[][] grupo = new KeyboardButton[dependentes.size()][1];
		
		int i = 0;
		for (Cliente d : dependentes) {
			grupo[i++][0] = new KeyboardButton(d.getNome());
		}
		
		ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(grupo);
		reply.oneTimeKeyboard(true);
		send.replyMarkup(reply);
		bot.execute(send);
		
		return ROUTER_REMOVER;
	}

	private String routerRemover() {
		String nome = message.text();
		
		Conta conta = session.get(CONTA, Conta.class);
		if (conta.removerDependente(new Cliente(nome))) {
			session.put(CONTA, conta);
			bot.execute(new SendMessage(chatId, "Dependente " + nome + " excluído com sucesso."));
			return null;
		} else {
			bot.execute(new SendMessage(chatId, "Não encontramos o dependente " + nome + ". Tente novamente informando um dependente corretamente."));
			return ROUTER_REMOVER;
		}
	}

}
