package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CLIENTE;
import static br.com.fiap.telegram.util.Keys.CONTA;

import java.math.BigDecimal;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;

public class CriarContaAction extends AbstractAction {
	
	private static final String ROUTER_CONTA = "routerConta";
	private static final String ROUTER_CLIENTE = "routerCliente";

	public String execute(String router) {
		switch (router) {
		
		case ROUTER_CLIENTE: 
			return routerCliente();
			
		case ROUTER_CONTA:			
			return routerConta();
			
		default: 
			return routerInit();
			
		}
		
	}

	private String routerInit() {
		
		if (session.containsKey(CONTA)) {
			Cliente cliente = session.get(CLIENTE, Cliente.class);
			Conta conta = session.get(CONTA, Conta.class);
			bot.execute(new SendMessage(chatId, cliente.getNome() + ", voc� j� possui uma conta com nosso banco. Sua conta � de n�mero " + conta.getNumero() + " aberta em " + conta.aberturaFormatada()));
			return null;
		}
		
		bot.execute(new SendMessage(chatId, "Seja bem vindo ao Banco Digital. Para criar uma nova conta precisaremos de algumas informa��es. Primeiramente informe seu nome"));
		
		return ROUTER_CLIENTE;
	}
	
	private String routerConta() {
		String valor = message.text();
		
		try {
			BigDecimal saldoInicial = new BigDecimal(valor);
			Cliente titular = session.get(CLIENTE, Cliente.class);			
			Conta conta = new Conta(titular, saldoInicial);
			
			session.put(CONTA, conta);			
			bot.execute(new SendMessage(chatId, "Sua conta foi criada com sucesso. " + conta));
			return null;
			
		} catch (NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Ops... N�o consegui reconhecer o valor '" + valor + "' tente novamente. Ex formato suportado --> 100.10 ou 100"));
			return ROUTER_CONTA;
		}
	}

	private String routerCliente() {
		Cliente cliente = new Cliente(message.text());

		session.put(CLIENTE, cliente);
		bot.execute(new SendMessage(chatId, "Parab�ns " + cliente + ", voc� est� quase l�. Agora informe o saldo inicial de sua conta. Ex: 100.50 ou 100"));
		
		return ROUTER_CONTA;
	}

	
	
}
