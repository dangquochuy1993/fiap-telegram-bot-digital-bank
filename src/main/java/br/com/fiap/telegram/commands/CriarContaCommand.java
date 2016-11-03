package br.com.fiap.telegram.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.Callback;
import br.com.fiap.telegram.Session;
import br.com.fiap.telegram.model.Cliente;

public class CriarContaCommand extends AbstractCommand {

	public CriarContaCommand() {
		super("/criar_conta", "Cria uma nova conta");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		Session.put("ultimoComando", this);
		
		Callback callback = Session.get("proximoComando", Callback.class);
		
		if (callback == null) {		
			Session.put("proximoComando", new Callback(getNome(), "nome"));		
			bot.execute(new SendMessage(chatId, "Informe seu nome"));
			return ;
		}
		
		switch (callback.getFlow()) {
			case "nome": 
				Session.put("proximoComando", new Callback(getNome(), "conta"));
				Cliente cliente = new Cliente(message.text());
				bot.execute(new SendMessage(chatId, "Seja bem vindo " + cliente));
				break;
			case "conta":
				
				System.out.println("em conta");
				
				break;
		}
		
	}

}
