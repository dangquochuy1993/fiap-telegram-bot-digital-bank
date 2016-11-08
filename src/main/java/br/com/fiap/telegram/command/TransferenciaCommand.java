package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.TransferenciaAction;

public class TransferenciaCommand extends AbstractCommand {

	public TransferenciaCommand() {
		super("/transferirconta", "Simular uma transfer�ncia de conta (Mudan�a de n�mero e limpeza dos hist�rico)");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		SendMessage send = new SendMessage(chatId, "Transfer�ncia de conta\nEssa op��o ir� resetar o n�mero de sua conta juntamente com o hist�rico. Seu saldo e dependentes ser�o mantidos.\nGostaria de continuar com o processo de migra��o?");
		
		KeyboardButton sim = new KeyboardButton("Sim");
		KeyboardButton nao = new KeyboardButton("N�o");
		
		KeyboardButton[] grupo = {sim, nao};
		
		ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(grupo);
		reply.oneTimeKeyboard(true);
		
		send.replyMarkup(reply);
		bot.execute(send);
		
		return new TransferenciaAction();
	}

}
