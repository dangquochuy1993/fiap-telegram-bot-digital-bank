package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.SaqueAction;

public class SaqueCommand extends AbstractCommand {
	
	public SaqueCommand() {
		super("/saque", "Saque de sua conta");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		bot.execute(new SendMessage(chatId, "Informe o valor que deseja sacar"));
		return new SaqueAction();
	}

}
