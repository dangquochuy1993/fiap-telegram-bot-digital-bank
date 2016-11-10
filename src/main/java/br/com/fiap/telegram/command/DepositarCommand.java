package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.DepositarAction;
import br.com.fiap.telegram.util.Helpers;

/**
 * Depositar um valor na conta
 * @author Diego.Saouda
 *
 */
public class DepositarCommand extends AbstractCommand {
	
	public DepositarCommand() {
		super("/depositar", "Depositar um valor em sua conta");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		SendMessage send = new SendMessage(chatId, "Depósito\nClique no valor desejado. Se preferir digite um valor no formato '0.00'.");
		send.replyMarkup(Helpers.getTelegramValoresButton());
		bot.execute(send);		
		
		return new DepositarAction();
	}

}
