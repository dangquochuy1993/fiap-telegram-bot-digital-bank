package br.com.fiap.telegram.command;

import static br.com.fiap.telegram.util.SessionManagerKey.KEY_CLIENTE;
import static br.com.fiap.telegram.util.SessionManagerKey.KEY_CONTA;
import static br.com.fiap.telegram.util.SessionManagerKey.KEY_ROUTER;
import static br.com.fiap.telegram.util.SessionManagerKey.KEY_ULTIMO_COMANDO;

import java.math.BigDecimal;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.util.Router;
import br.com.fiap.telegram.util.SessionManager;

public class CriarContaCommand extends AbstractCommand {

	private static final String ROUTER_ACTION_CLIENTE = "CLIENTE";
	private static final String ROUTER_ACTION_CONTA = "CONTA";

	private SessionManager session;
	
	public CriarContaCommand() {
		super("/criarconta", "Cria uma nova conta");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {		
		session = SessionManager.getInstance(user.id());
		Router router = session.get(KEY_ROUTER, Router.class);

		if (router == null) {		
			session.put(KEY_ROUTER, new Router(getNome(), ROUTER_ACTION_CLIENTE)).save();		
			bot.execute(new SendMessage(chatId, "Seja bem vindo ao Banco Digital. Para criar uma nova conta precisaremos de algumas informações. Primeiramente informe seu nome"));
			return ;
		}

		switch (router.getAction()) {
		case ROUTER_ACTION_CLIENTE: 
			routerCliente(bot, chatId, message);
			break;
			
		case ROUTER_ACTION_CONTA:			
			routerConta(bot, chatId, message);			
			break;
		}
	}

	private void routerConta(TelegramBot bot, Long chatId, Message message) {
		String valor = message.text();
		
		try {
			BigDecimal saldoInicial = new BigDecimal(valor);
			Cliente titular = session.get(KEY_CLIENTE, Cliente.class);			
			Conta conta = new Conta(titular, saldoInicial);
			
			session.put(KEY_CONTA, conta).save();			
			bot.execute(new SendMessage(chatId, "Sua conta foi criada com sucesso. " + conta));
			
			//comando finalizado
			session.remove(KEY_ROUTER);
			session.remove(KEY_ULTIMO_COMANDO);
			session.save();
			
		} catch (NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Ops... Não consegui reconhecer o valor '" + valor + "' tente novamente. Ex formato suportado --> 100.10 ou 100"));
		}
		
		
	}

	private void routerCliente(TelegramBot bot, Long chatId, Message message) {
		Cliente cliente = new Cliente(message.text());

		session.put(KEY_CLIENTE, cliente).save();
		proximoPasso(ROUTER_ACTION_CONTA);
		bot.execute(new SendMessage(chatId, "Parabéns " + cliente + ", você está quase lá. Agora informe o saldo inicial de sua conta. Ex: 100.50 ou 100"));		
	}

	private void proximoPasso(String proximoPasso) {
		session.put(KEY_ROUTER, new Router(getNome(), proximoPasso)).save();
	}
}
