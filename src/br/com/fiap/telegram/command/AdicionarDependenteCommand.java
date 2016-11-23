package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.action.AdicionarDependenteAction;

/**
 * Comando inicia action de adicionar dependente
 * @author Diego.Saouda
 *
 */
public class AdicionarDependenteCommand extends AbstractCommand {

	public AdicionarDependenteCommand() {
		super("/adicionardependente", "Adicionar um dependente a conta sua conta");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		
		bot.execute(new SendMessage(chatId, "Opção Adicionar Dependente\n\nInforme o nome do dependente."));		
		return new AdicionarDependenteAction();
	}

}
