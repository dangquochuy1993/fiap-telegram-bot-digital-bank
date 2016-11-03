package br.com.fiap.telegram.handler;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.request.ChatAction;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendChatAction;
import com.pengrad.telegrambot.request.SendMessage;
import com.pengrad.telegrambot.response.GetUpdatesResponse;

import br.com.fiap.telegram.actions.AbstractActions;
import br.com.fiap.telegram.actions.CallbackData;
import br.com.fiap.telegram.commands.AbstractCommand;
import br.com.fiap.telegram.exceptions.NaoEhUmComandoException;
import br.com.fiap.telegram.factory.TelegramFactory;

public class TelegramHandler implements Runnable {

	private int ultimoFlow = 0;
	private static final int FLUXO_COMANDO = 1;
	private static final int FLUXO_ACTION = 2;
	private static final int FLUXO_NAO_RECONHECIDO = 3;

	private TelegramBot bot;
	private GetUpdatesResponse updatesResponse;
	private int offset = 0;

	private Map<String, AbstractCommand> commands = new HashMap<>();
	private Map<String, AbstractActions> actions = new HashMap<>();

	private CallbackQuery ultimoCallbackQuery;
	
	public TelegramHandler() {
		bot = TelegramFactory.create();
	}

	public TelegramHandler addCommand(AbstractCommand command) {
		commands.put(command.getNome(), command);
		return this;
	}

	public TelegramHandler addAction(AbstractActions action) {
		actions.put(action.getNome(), action);
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

			switch(detectarFluxo(u)) {

			case FLUXO_COMANDO:
				commandFlow(u);
				break;

			case FLUXO_ACTION:
				callbackFlow(u);
				break;

			case FLUXO_NAO_RECONHECIDO:
			default:	

				System.out.println("OUTRO");

				break;	
			}

		});

		return true;
	}

	private void commandFlow(Update u) {	
		Long chatId = u.message().chat().id();				
		Message message = u.message();

		String texto = message.text();

		try {
			AbstractCommand comando = getComando(texto);
			comando.executar(bot, message);


		} catch (NaoEhUmComandoException | NullPointerException e) {
			bot.execute(new SendChatAction(chatId, ChatAction.typing.name()));
			bot.execute(new SendMessage(chatId, "Não reconheci o seu comando, tente novamente por favor"));
		}

	}

	private void callbackFlow(Update u) {
		CallbackQuery callbackQuery = u.callbackQuery();
		
		if (callbackQuery != null) {
			ultimoCallbackQuery = callbackQuery;
		} else {
			callbackQuery = ultimoCallbackQuery;
		}
		
		CallbackData callbackData = CallbackData.fromJson(callbackQuery.data());
		
		Message messageInput = u.message();
		
		AbstractActions action = actions.get(callbackData.getAction());
		if (messageInput == null) {
			action.executarButton(bot, callbackQuery.message(), callbackData);
		} else {
			action.executarInput(bot, callbackQuery.message(), messageInput, callbackData);
		}
	}

	private int detectarFluxo(Update u) {
		
		if (isCallback(u)) {
			ultimoFlow = FLUXO_ACTION;
			return FLUXO_ACTION;
		}

		if (isComando(u)) {
			ultimoFlow = FLUXO_COMANDO;
			return FLUXO_COMANDO;
		}
		
		//isso permite continuar um fluxo iniciado
		if (ultimoFlow > 0) {
			return ultimoFlow;
		}

		return FLUXO_NAO_RECONHECIDO;
	}

	private boolean isCallback(Update u) {
		CallbackQuery callback = u.callbackQuery();
		return callback != null;
	}

	private boolean isComando(Update u) {
		final Message message = u.message();		
		return message == null ? false : AbstractCommand.isCommand(message.text());
	}

	private void nextOffset(Update u) {
		offset = u.updateId() + 1;
	}

	private AbstractCommand getComando(String texto) throws NaoEhUmComandoException {
		String nomeComando = AbstractCommand.extrairNomeComando(texto);
		return commands.get(nomeComando);
	}
}
