package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import br.com.fiap.telegram.action.AbstractActions;
import br.com.fiap.telegram.action.ExtratoAction;

public class ExtratoCommand extends AbstractCommand {

	public ExtratoCommand() {
		super("/extrato", "Exibir o extrato de sua conta");
	}

	@Override
	protected AbstractActions execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		new ExtratoAction().execute(bot, message);
		return null;
	}

}
