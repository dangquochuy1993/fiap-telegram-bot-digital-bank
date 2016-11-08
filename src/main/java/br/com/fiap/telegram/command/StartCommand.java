package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;

public class StartCommand extends AbstractCommand {

	public StartCommand() {
		super("/start", "Tela de boas vindas.");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		bot.execute(new SendMessage(chatId, "Seja bem vindo ao banco digital. Voc� pode digitar / (barra) para ver as op��es que possuo para ajud�-lo. Vamos come�ar? Clique no comando abaixo:\n\n/criarconta"));
		return null;
	}
}
