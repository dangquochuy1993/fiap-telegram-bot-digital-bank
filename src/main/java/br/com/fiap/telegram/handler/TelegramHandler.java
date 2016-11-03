package br.com.fiap.telegram.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
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

public class TelegramHandler implements Runnable {

	private TelegramBot bot;
	private GetUpdatesResponse updatesResponse;
	private int offset=0;

	private Map<String, AbstractCommand> commands = new HashMap<>();

	public TelegramHandler() {
		bot = TelegramFactory.create();
	}

	public TelegramHandler addCommand(AbstractCommand command) {
		commands.put(command.getNome(), command);
		return this;
	}

	public void run() {


		while (true) {

			updatesResponse =  bot.execute(new GetUpdates().limit(100).offset(offset));
			List<Update> updates = updatesResponse.updates();

			updates.stream().forEach(update -> {

				CallbackQuery callbackQuery = update.callbackQuery();

				if (callbackQuery != null) {
					
					System.out.println(update.editedMessage());
					
					AnswerCallbackQuery answer = new AnswerCallbackQuery(callbackQuery.id());
					answer.text("recebido o aperto do botão");
					
					System.out.println(callbackQuery);

					//bot.execute(answer);
					bot.execute(new EditMessageText(callbackQuery.inlineMessageId(), "OK"));
					
					return ;
				}

				if (update.message() == null) {
					return ;
				}


				Long chatId = update.message().chat().id();
				offset = update.updateId() + 1;
				Message message = update.message();

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

		}	
	}

	private AbstractCommand getComando(String texto) throws NaoEhUmComandoException {
		String nomeComando = AbstractCommand.extrairNomeComando(texto);
		return commands.get(nomeComando);
	}
}
