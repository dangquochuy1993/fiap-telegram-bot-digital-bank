package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CLIENTE;
import static br.com.fiap.telegram.util.Keys.CONTA;

import java.math.BigDecimal;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.DadosBasicoPrinter;
import br.com.fiap.telegram.util.Helpers;

public class CriarContaAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
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
			
			String message = cliente.getNome() + ", você já possui uma conta com nosso banco. Sua conta é de número " + conta.getNumero() + " aberta em " + Helpers.formatarDataHora(conta.getAbertura());
			bot.execute(new SendMessage(chatId, message));
			return null;
		}
		
		bot.execute(new SendMessage(chatId, "Seja bem vindo ao Banco Digital. Para criar uma nova conta precisaremos de algumas informações.\nInforme seu nome completo"));
		
		return ROUTER_CLIENTE;
	}
	
	private String routerConta() {
		String valor = message.text();
		
		try {
			BigDecimal saldoInicial = new BigDecimal(valor);
			Cliente titular = session.get(CLIENTE, Cliente.class);			
			Conta conta = new Conta(titular, saldoInicial);
			
			session.put(CONTA, conta);			
			bot.execute(new SendMessage(chatId, "Sua conta foi criada com sucesso. " + new DadosBasicoPrinter().imprimir(conta)));
			return null;
			
		} catch (NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Ops... Não consegui reconhecer o valor '" + valor + "' tente novamente. Ex formato suportado --> 100.10 ou 100"));
			return ROUTER_CONTA;
		}
	}

	private String routerCliente() {
		Cliente cliente = new Cliente(message.text());

		session.put(CLIENTE, cliente);
		bot.execute(new SendMessage(chatId, "Parabéns " + cliente.getNome() + ", você está quase lá. Agora informe o saldo inicial de sua conta. \nObs: Informe no seguinte formato 0.00"));
		
		return ROUTER_CONTA;
	}

	
	
}
