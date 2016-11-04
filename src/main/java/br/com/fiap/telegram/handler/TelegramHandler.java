package br.com.fiap.telegram.handler;

import static br.com.fiap.telegram.SessionManagerKey.KEY_FLUXO_STAGE;
import static br.com.fiap.telegram.SessionManagerKey.KEY_ULTIMO_COMANDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.CallbackQuery;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.Router;
import br.com.fiap.telegram.SessionManager;
import br.com.fiap.telegram.actions.AbstractActions;
import br.com.fiap.telegram.commands.AbstractCommand;
import br.com.fiap.telegram.exceptions.NaoEhUmComandoException;
import br.com.fiap.telegram.factory.TelegramFactory;

public class TelegramHandler implements Runnable {

	private TelegramBot bot;
	private int offset = 0;

	private Map<String, AbstractCommand> commands = new HashMap<>();
	private Map<String, AbstractActions> actions = new HashMap<>();

	public TelegramHandler() {
		SessionManager.put(KEY_FLUXO_STAGE, Fluxo.NENHUM);
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
	
	/**
	 * Executa para sempre
	 */
	public void run() {		
		while(execute());
	}

	/**
	 * Executando o telegram
	 * @return
	 */
	private boolean execute() {
		GetUpdates request = new GetUpdates().offset(offset);
		List<Update> updates = bot.execute(request).updates();
		Stream<Update> stream = updates.stream();
		
		stream.forEach(u -> {				
			nextUpdateOffset(u);

			switch(routerFluxo(u)) {

			case COMANDO: executeFluxoCommand(u); break;
			case ACAO: executeFluxoAction(u); break;
			case NAO_RECONHECIDO:
			
			default:
				System.out.println("QUE ACAO TOMAR?");
				break;	
			}

		});

		return true;
	}

	/**
	 * Executando um fluxo de comando
	 * @param u
	 */
	private void executeFluxoCommand(Update u) {	
		AbstractCommand command = SessionManager.get(KEY_ULTIMO_COMANDO, AbstractCommand.class);
		
		if (command != null) {
			command.onUpdateReceived(bot, u);
			return ;
		}
						
		String mensagem = u.message().text();		

		try {
			command = getComando(mensagem);
			command.onUpdateReceived(bot, u);

		} catch (NaoEhUmComandoException | NullPointerException e) {
			Long chatId = u.message().chat().id();
			bot.execute(new SendMessage(chatId, "Não reconheci o seu comando, tente novamente por favor"));
		}

	}

	/**
	 * Executando o fluxo de action
	 * @param u
	 */
	private void executeFluxoAction(Update u) {
		CallbackQuery callbackQuery = u.callbackQuery();
		
		if (callbackQuery != null) {
			SessionManager.put("callbackQuery", callbackQuery);
		} else {
			callbackQuery = SessionManager.get("callbackQuery", CallbackQuery.class);
		}
		
		Router callbackData = Router.fromJson(callbackQuery.data());
		
		Message messageInput = u.message();
		
		AbstractActions action = actions.get(callbackData.getController());
		if (messageInput == null) {
			action.executarButton(bot, callbackQuery.message(), callbackData);
		} else {
			action.executarInput(bot, callbackQuery.message(), messageInput, callbackData);
		}
	}
	
	/**
	 * Detectar para qual fluxo o programa vai ser direcionado (de acordo com ações do usuário no chat)
	 * @param u
	 * @return
	 */
	private Fluxo routerFluxo(Update u) {
		
		if (isFluxoContinuacao()) {
			return SessionManager.get(KEY_FLUXO_STAGE, Fluxo.class);
		}
		
		if (isFluxoAction(u)) {			
			return Fluxo.ACAO;
		}

		if (isFluxoComando(u)) {			
			return Fluxo.COMANDO;
		}

		SessionManager.put(KEY_FLUXO_STAGE, Fluxo.NAO_RECONHECIDO);
		return Fluxo.NAO_RECONHECIDO;
	}

	/**
	 * Verificar se tem algum fluxo em andamento para continuá-lo
	 * @return
	 */
	private boolean isFluxoContinuacao() {
		return !SessionManager.get(KEY_FLUXO_STAGE, Fluxo.class).equals(Fluxo.NENHUM);
	}

	/**
	 * Verifica se o fluxo do programa deve ser ação (actions)
	 * @param u
	 * @return
	 */
	private boolean isFluxoAction(Update u) {
		CallbackQuery callback = u.callbackQuery();
		if (callback != null) {
			SessionManager.put(KEY_FLUXO_STAGE, Fluxo.ACAO);
			return true;
		}
		
		return false;
	}

	/**
	 * Verifica se o fluxo do programa deve ser comando (command)
	 * @param u
	 * @return
	 */
	private boolean isFluxoComando(Update u) {
		final Message message = u.message();		
		
		if (message != null && AbstractCommand.isCommand(message.text())) {
			SessionManager.put(KEY_FLUXO_STAGE, Fluxo.COMANDO);
			return true;
		}  		
		
		return false;		
	}

	/**
	 * Movendo o offeset de mensagens do telegram para pegar as mensagens mais recentes
	 * @param u
	 */
	private void nextUpdateOffset(Update u) {		
		offset = u.updateId() + 1;
	}

	/**
	 * Pegando um comando
	 * @param texto
	 * @return
	 * @throws NaoEhUmComandoException
	 */
	private AbstractCommand getComando(String texto) throws NaoEhUmComandoException {
		String nomeComando = AbstractCommand.extrairNomeComando(texto);
		return commands.get(nomeComando);
	}
}
