package br.com.fiap.telegram.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.actions.CriarContaAction;

public class CriarContaCommand extends AbstractCommand {

	public CriarContaCommand() {
		super("/criar_conta", "Cria uma nova conta");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		new SendMessage(chatId, "Precisamos de algumas informções para criar sua conta");
		
		new CriarContaAction().executar(bot, message);
	}

}
