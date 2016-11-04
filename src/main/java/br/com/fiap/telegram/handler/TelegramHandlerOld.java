/*
package br.com.fiap.telegram.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.InlineQueryResult;
import com.pengrad.telegrambot.request.AnswerCallbackQuery;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import br.com.fiap.telegram.commands.AbstractCommand;
import br.com.fiap.telegram.exceptions.NaoEhUmComandoException;
import br.com.fiap.telegram.factory.TelegramFactory;

public class TelegramHandlerOld implements Runnable {

	private TelegramBot bot;
	private GetUpdatesResponse updatesResponse;
	private int offset = 0;

	private Map<String, AbstractCommand> commands = new HashMap<>();

	public TelegramHandlerOld() {
		bot = TelegramFactory.create();
	}

	public TelegramHandlerOld addCommand(AbstractCommand command) {
		commands.put(command.getNome(), command);
		return this;
	}

	public void run() {		
		while(execute());
	}
	
	private boolean execute() {
		updatesResponse =  bot.execute(new GetUpdates().offset(offset).timeout(1));

		List<Update> updates = updatesResponse.updates();
		
		updates.stream().forEach(u -> {				
			nextOffset(u);
			
			CallbackQuery callback = u.callbackQuery();

			if (callback != null) {
				System.out.println(callback);
				
				System.out.println(callback.message().chat());
				
				EditMessageText edit = new EditMessageText(callback.message().chat().id(), callback.message().messageId(), "TEXTO EDITADO???");
				InlineKeyboardButton ikb1 = new InlineKeyboardButton("ok1");
				ikb1.callbackData("callbackData data teste");
				
				
				InlineKeyboardButton[] keyboards = {ikb1};
				InlineKeyboardMarkup i = new InlineKeyboardMarkup(keyboards);
				
				edit.replyMarkup(i);
				bot.execute(edit);
				
				
				//enviando mensagem no chat pelo callback
				//bot.execute(new SendMessage(callback.message().chat().id(), "callback executado"));
				

				//AnswerCallbackQuery answer = new AnswerCallbackQuery(callback.id());
				//answer.text("recebido o aperto do botão");

				

				//bot.execute(answer);
				//bot.execute(new EditMessageText(callback.inlineMessageId(), "OK"));

				return ;
			}

			if (u.message() == null) {
				return ;
			}


			Long chatId = u.message().chat().id();				
			Message message = u.message();

			String texto = message.text();

			System.out.println(texto);

			try {
				AbstractCommand comando = getComando(texto);
				comando.executar(bot, message);


			} catch (NaoEhUmComandoException | NullPointerException e) {
				bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));
				bot.execute(new SendMessage(chatId, "Não reconheci o que você falou, por isso estou lhe enviando uma ajuda"));
			}
		});
		
		return true;
	}

	private void nextOffset(Update u) {
		offset = u.updateId() + 1;
	}

	private AbstractCommand getComando(String texto) throws NaoEhUmComandoException {
		String nomeComando = AbstractCommand.extrairNomeComando(texto);
		return commands.get(nomeComando);
	}
}
*/
