package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import java.math.BigDecimal;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;

public class SaqueAction extends AbstractAction {
	
	private static final String ROUTER_SAQUE = "routerSaque";

	public String execute(String router) {
		switch (router) {
		
		default: 
		case ROUTER_SAQUE:			
			return routerSaque();
		}
		
	}

	private String routerSaque() {
		try {
			BigDecimal valor = new BigDecimal(message.text());
			Conta conta = session.get(CONTA, Conta.class);
			conta.saque(valor);
			
			session.put(CONTA, conta);
					
			bot.execute(new SendMessage(chatId, conta.toString()));
			return null;
			
		} catch(NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Valor informado para saque não é válido."));
			return ROUTER_SAQUE;
		}
	}
	
}
