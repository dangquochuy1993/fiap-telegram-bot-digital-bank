package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.EmprestimoAction;
import br.com.fiap.telegram.model.Taxas;

public class EmprestimoCommand extends AbstractCommand {

	public EmprestimoCommand() {
		super("/emprestimo", "Solicitar empr�stimo. TAXA " + Taxas.EMPRESTIMO);
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		SendMessage send = new SendMessage(chatId, "Empr�stimo\n\nEm quanto tempo voc� pretende pagar esse empr�stimo?\nCaso as op��es n�o te atenda voc� pode digitar. Ex formato aceito\n\n1 ano\n2 meses\n1 mes\n2 anos");
		
		KeyboardButton op1 = new KeyboardButton("12 meses");
		KeyboardButton op2 = new KeyboardButton("24 meses");
		KeyboardButton op3 = new KeyboardButton("36 meses");
		
		KeyboardButton[] grupo1 = {op1, op2, op3};
		
		ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(grupo1);
		reply.oneTimeKeyboard(true);
		
		send.replyMarkup(reply);
		bot.execute(send);
		
		return new EmprestimoAction();
	}

}
