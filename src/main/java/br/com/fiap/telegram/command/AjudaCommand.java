package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;

public class AjudaCommand extends AbstractCommand {

	private String comandos;

	public AjudaCommand(String comandos) {
		super("/ajuda", "Menu de ajuda");
		this.comandos = comandos;
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {

		bot.execute(new SendMessage(chatId, "Ajuda\nA seguir os comandos disponíveis para sua utilização em novo banco:\n" + comandos));
		
		return null;
	}

}
