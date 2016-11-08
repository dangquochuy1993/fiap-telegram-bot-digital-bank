package br.com.fiap.telegram.handler;

import static br.com.fiap.telegram.util.Keys.CONTA;
import static br.com.fiap.telegram.util.Keys.NEXT_ACTION;
import static br.com.fiap.telegram.util.Keys.ROUTER;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.command.AbstractCommand;
import br.com.fiap.telegram.command.CriarContaCommand;
import br.com.fiap.telegram.command.StartCommand;
import br.com.fiap.telegram.exception.IsNotCommandException;
import br.com.fiap.telegram.factory.TelegramFactory;
import br.com.fiap.telegram.util.SessionManager;

public class TelegramHandler implements Runnable {

	private TelegramBot bot;
	private int offset = 0;

	private Map<String, AbstractCommand> commands = new LinkedHashMap<>();

	public TelegramHandler() {
		bot = TelegramFactory.create();
		
		//comandos obrigatórios
		addCommand(new StartCommand());
		addCommand(new CriarContaCommand());
	}

	public TelegramHandler addCommand(AbstractCommand command) {
		commands.put(command.getName(), command);
		return this;
	}
	
	/**
	 * Colocar o resultado no botfather para ajudar o usuário
	 * @return
	 */
	public String printCommands() {
		StringBuilder sb = new StringBuilder();
		
		commands.forEach((key, command) -> {			
			sb.append("\n" + command.getName() + " - " + command.getDescription());
		});
		
		return sb.toString();		
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
			
			switch(routerWorkFlow(u)) {

			case COMMAND: executeWorkFlowCommand(u); break;
			case ACTION: executeWorkFlowAction(u); break;
			case UNKNOWN:			
			default:
				executeWorkFlowUnknown(u);
				break;	
			}
		});

		return true;
	}

	private void executeWorkFlowUnknown(Update u) {
		bot.execute(new SendMessage(u.message().chat().id(), "Não reconheci o comando, tente novamente. Digite / para ver as opções disponíveis"));
	}

	/**
	 * Executando um fluxo de comando
	 * @param u
	 */
	private void executeWorkFlowCommand(Update u) {
		AbstractCommand command;
		String mensagem = u.message().text();		
		
		SessionManager session = SessionManager.getInstance(u.message().from().id());
		
		session.remove(ROUTER);
		session.remove(NEXT_ACTION);
		
		try {
			command = getCommand(mensagem);
			if (!(command instanceof CriarContaCommand) && !(command instanceof StartCommand) && !session.containsKey(CONTA)) {
				throw new IsNotCommandException("Crie uma conta para usar esses comandos");
			}
			
			AbstractAction action = command.onUpdateReceived(bot, u);
			if (action != null) {
				session.put(NEXT_ACTION, action);
			}
			
			
		} catch (IsNotCommandException | NullPointerException e) {
			Long chatId = u.message().chat().id();
			bot.execute(new SendMessage(chatId, "Ops ... não reconheci o seu comando ou você ainda não criou um conta."));
		}

	}

	/**
	 * Executando o fluxo de action
	 * @param u
	 */
	private void executeWorkFlowAction(Update u) {
		SessionManager session = SessionManager.getInstance(u.message().from().id());
		AbstractAction action = session.get(NEXT_ACTION, AbstractAction.class);
		action.execute(bot, u.message());
	}
	
	/**
	 * Detectar para qual fluxo o programa vai ser direcionado (de acordo com ações do usuário no chat)
	 * @param u
	 * @return
	 */
	private WorkFlow routerWorkFlow(Update u) {
		
		if (isWorkFlowCommand(u)) {			
			return WorkFlow.COMMAND;
		}

		if (isWorkFlowAction(u)) {			
			return WorkFlow.ACTION;
		}
		
		return WorkFlow.UNKNOWN;
	}

	private boolean isWorkFlowAction(Update u) {
		SessionManager session = SessionManager.getInstance(u.message().from().id());
		return session.containsKey(NEXT_ACTION);
	}

	/**
	 * Verifica se o fluxo do programa deve ser comando (command)
	 * @param u
	 * @return
	 */
	private boolean isWorkFlowCommand(Update u) {
		final Message message = u.message();		
		
		if (message != null && AbstractCommand.isCommand(message.text())) {
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
	 * @param message
	 * @return
	 * @throws IsNotCommandException
	 */
	private AbstractCommand getCommand(String message) throws IsNotCommandException {
		String commandName = AbstractCommand.extractCommandName(message);
		return commands.get(commandName);
	}
}
