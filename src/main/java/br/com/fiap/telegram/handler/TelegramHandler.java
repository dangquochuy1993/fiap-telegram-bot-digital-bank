package br.com.fiap.telegram.handler;

import static br.com.fiap.telegram.SessionManagerKey.KEY_FLUXO_STAGE;
import static br.com.fiap.telegram.SessionManagerKey.KEY_ROUTER;
import static br.com.fiap.telegram.SessionManagerKey.KEY_ULTIMO_COMANDO;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.actions.AbstractActions;
import br.com.fiap.telegram.commands.AbstractCommand;
import br.com.fiap.telegram.exceptions.NaoEhUmComandoException;
import br.com.fiap.telegram.factory.TelegramFactory;
import br.com.fiap.telegram.util.SessionManager;

public class TelegramHandler implements Runnable {

	private TelegramBot bot;
	private int offset = 0;

	private Map<String, AbstractCommand> commands = new HashMap<>();
	private Map<String, AbstractActions> actions = new HashMap<>();

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
			
			SessionManager session = SessionManager.getInstance(u.message().from().id());
			if (!session.containsKey(KEY_FLUXO_STAGE)) {
				session.put(KEY_FLUXO_STAGE, Fluxo.INICIAL).save();
			}

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
		AbstractCommand command;
		String mensagem = u.message().text();		
		
		SessionManager session = SessionManager.getInstance(u.message().from().id());
		
		try {
			command = getComando(mensagem);
			
			session.remove(KEY_ROUTER);
			//session.remove(KEY_ULTIMO_COMANDO);			
			session.put(KEY_ULTIMO_COMANDO, command);
			session.save();
			
			command.onUpdateReceived(bot, u);

		} catch (NaoEhUmComandoException | NullPointerException e) {
			
			command = session.get(KEY_ULTIMO_COMANDO, AbstractCommand.class);
			
			if (command != null) {
				command.onUpdateReceived(bot, u);
				return ;
			}
			
			Long chatId = u.message().chat().id();
			bot.execute(new SendMessage(chatId, "Ops ... não reconheci o seu comando, tente novamente por favor"));
		}

	}

	/**
	 * Executando o fluxo de action
	 * @param u
	 */
	private void executeFluxoAction(Update u) {
		
	}
	
	/**
	 * Detectar para qual fluxo o programa vai ser direcionado (de acordo com ações do usuário no chat)
	 * @param u
	 * @return
	 */
	private Fluxo routerFluxo(Update u) {
		SessionManager session = SessionManager.getInstance(u.message().from().id());
		
		if (isFluxoContinuacao(session)) {
			return session.get(KEY_FLUXO_STAGE, Fluxo.class);
		}
		
		if (isFluxoAction(u)) {			
			return Fluxo.ACAO;
		}

		if (isFluxoComando(u, session)) {			
			return Fluxo.COMANDO;
		}

		session.put(KEY_FLUXO_STAGE, Fluxo.NAO_RECONHECIDO).save();
		return Fluxo.NAO_RECONHECIDO;
	}

	/**
	 * Verificar se tem algum fluxo em andamento para continuá-lo
	 * @return
	 */
	private boolean isFluxoContinuacao(SessionManager session) {
		
		System.out.println(session.get(KEY_FLUXO_STAGE, Fluxo.class));
		
		return !session.get(KEY_FLUXO_STAGE, Fluxo.class).equals(Fluxo.INICIAL);
	}

	/**
	 * Verifica se o fluxo do programa deve ser ação (actions)
	 * @param u
	 * @return
	 */
	private boolean isFluxoAction(Update u) {
		return false;
	}

	/**
	 * Verifica se o fluxo do programa deve ser comando (command)
	 * @param u
	 * @return
	 */
	private boolean isFluxoComando(Update u, SessionManager session) {
		final Message message = u.message();		
		
		if (message != null && AbstractCommand.isCommand(message.text())) {
			session.put(KEY_FLUXO_STAGE, Fluxo.COMANDO).save();
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
