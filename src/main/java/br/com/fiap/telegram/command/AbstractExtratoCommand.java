package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.AbstractExtratoAction;

abstract public class AbstractExtratoCommand extends AbstractCommand {

	public AbstractExtratoCommand(String name, String description) {
		super(name, description);
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		action().execute(bot, message);		
		return null;
	}

	protected abstract AbstractExtratoAction action();
}
