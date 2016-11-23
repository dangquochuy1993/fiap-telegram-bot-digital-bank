package br.com.fiap.telegram.command;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;

/**
 * Iniciando o processo de uso do BOT
 * @author Diego.Saouda
 *
 */
public class StartCommand extends AbstractCommand {

	public StartCommand() {
		super("/start", "Tela de boas vindas.");
	}

	@Override
	protected AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		bot.execute(new SendMessage(chatId, "Seja bem vindo ao Fiap Telegram Bot Digital Bank.\nDigite / (barra) para exibir as opções disponíveis.\nVamos começar?\nVocê pode iniciar criando uma conta, para isso digite /criarconta em seguida enter, ou se preferir clique em cima da opção.\n\nSempre que você tiver dúvidas você pode digitar /ajuda"));
		return null;
	}
}
