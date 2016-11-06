package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.CriarContaAction;

public class StartCommand extends AbstractCommand {

	public StartCommand() {
		super("/start", "Iniciando o bot");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		bot.execute(new SendMessage(chatId, "Seja bem vindo ao banco digital. Você pode digitar / (barra) para ver as opções que possuo para ajudá-lo. Vamos começar?"));
		bot.execute(new SendMessage(chatId, "/criarconta"));
		return null;
	}
}
