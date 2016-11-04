package br.com.fiap.telegram.commands;

import static br.com.fiap.telegram.SessionManagerKey.KEY_CONTA;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.SessionManager;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.model.HistoricoTransacoes;

public class ExtratoCommand extends AbstractCommand {
	
	public ExtratoCommand() {
		super("/extrato", "Exibir o extrato de sua conta");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		Conta conta = SessionManager.get(KEY_CONTA, Conta.class);
		HistoricoTransacoes historico = conta.extrato();
		
		bot.execute(new SendMessage(chatId, historico.toString()));
		
	}

}
