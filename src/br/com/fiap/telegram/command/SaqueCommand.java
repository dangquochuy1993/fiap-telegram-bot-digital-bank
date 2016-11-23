package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.SaqueAction;
import br.com.fiap.telegram.model.Taxas;
import br.com.fiap.telegram.util.Helpers;

/**
 * Saque da conta
 * @author Diego.Saouda
 *
 */
public class SaqueCommand extends AbstractCommand {
	
	public SaqueCommand() {
		super("/saque", "Saque um valor de sua conta");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		
		SendMessage send = new SendMessage(chatId, "Opção Saque (TAXA "+ Taxas.SAQUE.getValor() +")\n\nClique no valor desejado. Se preferir digite um valor no formato 0.00.");
		send.replyMarkup(Helpers.getTelegramValoresButton());
		bot.execute(send);
		
		return new SaqueAction();
	}
}
