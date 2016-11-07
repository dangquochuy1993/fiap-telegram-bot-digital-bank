package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;

public class RemoverDependenteAction extends AbstractAction {


	private static final String ROUTER_REMOVER = "routerRemover";

	@Override
	protected String execute(String router) {
		
		switch(router) {
		
			default:
				return routerInit();	
				
			case ROUTER_REMOVER:
				return routerRemover();
		}
		
		
		Cliente dependente = new Cliente(message.text());
		conta.adicionarDepentente(dependente);
		
		bot.execute(new SendMessage(chatId, "Novo depentente ("+ dependente.getNome() +") adicionado com Sucesso"));
		
		return null;
	}

	private String routerInit() {
		Conta conta = session.get(CONTA, Conta.class);
		SendMessage send = new SendMessage(chatId, "Remover Dependente\nInforme o nome do dependente ou cliente em um dos botões");
		
		KeyboardButton[] grupo = new KeyboardButton()[10];
		conta.getDependentes().forEach(d -> {
			
		});
		
		 = {new KeyboardButton("5")};
		
		ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(grupo);
		reply.oneTimeKeyboard(true);
		
		send.replyMarkup();
		bot.execute(send);
		return ROUTER_REMOVER;
	}

	private String routerRemover() {
		// TODO Auto-generated method stub
		return ROUTER_REMOVER;
	}

}
