package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import java.math.BigDecimal;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;

public class DepositarAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	private static final String ROUTER_DEPOSITAR = "routerDepositar";

	public String execute(String router) {
		switch (router) {
		
		default: 
		case ROUTER_DEPOSITAR:			
			return routerDepositar();
		}
		
	}

	private String routerDepositar() {
		try {
			BigDecimal valor = new BigDecimal(message.text());
			Conta conta = session.get(CONTA, Conta.class);
			conta.depositar(valor);
			
			session.put(CONTA, conta);
			
			bot.execute(new SendMessage(chatId, "Depósito efeturado com sucesso. Seu saldo atual é " + conta.getSaldo()));
			return null;
			
		} catch(NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Valor informado para deposito não é válido."));
			return ROUTER_DEPOSITAR;
		}
	}
	
}
