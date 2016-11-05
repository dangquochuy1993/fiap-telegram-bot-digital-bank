package br.com.fiap.telegram.commands;

import static br.com.fiap.telegram.SessionManagerKey.KEY_ROUTER;
import static br.com.fiap.telegram.SessionManagerKey.KEY_CLIENTE;
import static br.com.fiap.telegram.SessionManagerKey.KEY_CONTA;
import static br.com.fiap.telegram.SessionManagerKey.KEY_ULTIMO_COMANDO;

import java.math.BigDecimal;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.Router;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.util.SessionManager;

public class SaqueCommand extends AbstractCommand {
	
	private static final String ROUTER_ACTION_SAQUE = "saque";

	public SaqueCommand() {
		super("/saque", "Saque de um valor");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		SessionManager session = SessionManager.getInstance(user.id());
		
		Router router = session.get(KEY_ROUTER, Router.class);

		if (router == null) {		
			session.put(KEY_ROUTER, new Router(getNome(), ROUTER_ACTION_SAQUE)).save();		
			bot.execute(new SendMessage(chatId, "Informe o valor que deseja sacar"));
			return ;
		}
		
		try {
			BigDecimal valor = new BigDecimal(message.text());
			Conta conta = session.get(KEY_CONTA, Conta.class);
			conta.saque(valor);
					
			bot.execute(new SendMessage(chatId, conta.toString()));
			
			//comando finalizado
			session.remove(KEY_ROUTER);
			session.remove(KEY_ULTIMO_COMANDO);
			session.save();
			
		} catch(NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Valor informado para saque inv�lido n�o � v�lido."));
		}
	}

}
