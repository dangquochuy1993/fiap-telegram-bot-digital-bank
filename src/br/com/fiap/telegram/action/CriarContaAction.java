package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CLIENTE;
import static br.com.fiap.telegram.util.Keys.CONTA;

import java.math.BigDecimal;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.DadosBasicoPrinter;
import br.com.fiap.telegram.util.Helpers;

/**
 * Action responsável pela criação de uma conta.
 * @author diego
 *
 */
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

	/**
	 * Processo inicial de criação de conta. Se uma conta já existir o programa será encerrado.
	 * @return null caso o usuário já tenha conta ou o nome da próxima rota para que o programa possa seguir com a execução.
	 */
	private String routerInit() {
		
		if (session.containsKey(CONTA)) {
			Cliente cliente = session.get(CLIENTE, Cliente.class);
			Conta conta = session.get(CONTA, Conta.class);
			
			String message = cliente.getNome() + ", você já possui uma conta em nosso banco. O número de sua conta é " + conta.getNumero() + " aberta em " + Helpers.formatarDataHora(conta.getAbertura());
			bot.execute(new SendMessage(chatId, message));
			return null;
		}
		
		bot.execute(new SendMessage(chatId, "Opçao Criar Conta.\n\nPrecisaremos de algumas informações.\nInforme seu nome completo:"));
		
		return ROUTER_CLIENTE;
	}
	
	/**
	 * Executa a rota de criação da conta, aonde o usuário deverá informar o valor que iniciará a conta.
	 * @return Rota da conta em caso de erro na digitação ou null para informar que a action foi finalizada.
	 */
	private String routerConta() {
		String valor = message.text();
		
		try {
			BigDecimal saldoInicial = new BigDecimal(valor);
			Cliente titular = session.get(CLIENTE, Cliente.class);			
			Conta conta = new Conta(titular, saldoInicial);
			
			session.put(CONTA, conta);			
			bot.execute(new SendMessage(chatId, "Sua conta foi criada com sucesso. " + new DadosBasicoPrinter().imprimir(conta) + "\n\nClique em /ajuda para visualizar as opções disponíveis."));
			return null;
			
		} catch (NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Ops... Não consegui reconhecer o valor '" + valor + "' tente novamente. Use o formato suportado --> 100.10 ou 100"));
			return ROUTER_CONTA;
		}
	}

	/**
	 * Recebe mensagem digitada pelo usuário. Esse mensagem deve ser o nome do usuário
	 * @return Informa o nome da rota da conta para que o usuário informe o saldo da conta.
	 */
	private String routerCliente() {
		Cliente cliente = new Cliente(message.text());

		session.put(CLIENTE, cliente);
		bot.execute(new SendMessage(chatId, "Parabéns " + cliente.getNome() + ", você está quase lá. Agora informe o saldo inicial de sua conta.\nInforme no formato suportado --> 0.00"));
		
		return ROUTER_CONTA;
	}

	
	
}
