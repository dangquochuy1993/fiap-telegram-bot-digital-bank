package br.com.fiap.telegram.commands;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import br.com.fiap.telegram.actions.CriarContaAction;

public class HelpCommand extends AbstractCommand {

	public HelpCommand() {
		super("/help", "Ajuda para uso do bot");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		new CriarContaAction().executar(bot, message);
	}

}
