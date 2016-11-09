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
		super("/transferirconta", "Simular uma transferência de conta (Mudança de número e limpeza dos histórico)");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		SendMessage send = new SendMessage(chatId, "Transferência de conta\nEssa opção irá resetar o número de sua conta juntamente com o histórico. Seu saldo e dependentes serão mantidos.\nGostaria de continuar com o processo de migração?");
		
		KeyboardButton sim = new KeyboardButton("Sim");
		KeyboardButton nao = new KeyboardButton("Não");
		
		KeyboardButton[] grupo = {sim, nao};
		
		ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(grupo);
		reply.oneTimeKeyboard(true);
		
		send.replyMarkup(reply);
		bot.execute(send);
		
		return new TransferenciaAction();
	}

}
