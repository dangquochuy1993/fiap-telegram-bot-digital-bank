package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

public class StartCommand extends AbstractCommand {

	public StartCommand() {
		super("/start", "Iniciando o bot");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		/*
		SendMessage send = new SendMessage(chatId, "Estou no start");
		send.parseMode(ParseMode.Markdown);
		
		
		KeyboardButton kb1 = new KeyboardButton("/OK 1");
		
		KeyboardButton[] b1 = {kb1, new KeyboardButton("OK 2")};
		KeyboardButton[] b2 = {new KeyboardButton("OK 3"), new KeyboardButton("OK 4")};

		send.replyMarkup(new ReplyKeyboardMarkup(b1, b2));
		*/
		
		
		bot.execute(new SendMessage(chatId, "Seja bem vindo ao banco digital. Você pode digitar / (barra) para ver as opções que possuo para ajudá-lo. Vamos começar?"));
		bot.execute(new SendMessage(chatId, "/criarconta"));
	}

}
