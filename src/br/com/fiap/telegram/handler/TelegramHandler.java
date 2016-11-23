package br.com.fiap.telegram.handler;

import static br.com.fiap.telegram.util.Keys.CONTA;
import static br.com.fiap.telegram.util.Keys.NEXT_ACTION;
import static br.com.fiap.telegram.util.Keys.ROUTER;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.request.GetUpdates;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.command.AbstractCommand;
import br.com.fiap.telegram.command.AjudaCommand;
import br.com.fiap.telegram.command.CriarContaCommand;
import br.com.fiap.telegram.command.StartCommand;
import br.com.fiap.telegram.exception.IsNotCommandException;
import br.com.fiap.telegram.factory.TelegramFactory;
import br.com.fiap.telegram.util.Logger;
import br.com.fiap.telegram.util.SessionManager;

/**
 * Handler (manipulador) do telegram.
 * Essa classe tem como objetivo intermediar o uso da api do telegram com o negócio.
 * Toda mensagem recebida pelo telegram passa por um analisador de fluxo de trabalho.
 * Esse analisador irá delegar qual rumo o programa deve tomar. Os fluxo aceitos atualmente são especificado na 
 * enum {@link WorkFlow}
 * @author Diego.Saouda
 *
 */
public class TelegramHandler implements Runnable {

	/**
	 * Pool de thread que permite o sistema interagir com mais de um usuário simultaneamente
	 */
	private final ExecutorService pool = Executors.newFixedThreadPool(100);
	
	/**
	 * API do telegram utilizado para comunicação
	 */
	private TelegramBot bot;
	
	/**
	 * offset de mensagens, controla qual a próxima mensagem que a api deverá pegar. 
	 * Isso garente que a api não retorne mensagens já processadas / lidas pelo BOT (programa).
	 */
	private int offset = 0;

	/**
	 * Armazena os comandos que o handler aceita
	 */
	private Map<String, AbstractCommand> commands = new LinkedHashMap<>();

	public TelegramHandler() {
		bot = TelegramFactory.create();
		
		//comandos obrigatórios
		addCommand(new StartCommand());
		addCommand(new CriarContaCommand());
	}

	/**
	 * adicionando novos comandos
	 * @param command
	 * @return
	 */
	public TelegramHandler addCommand(AbstractCommand command) {
		commands.put(command.getName(), command);
		return this;
	}
	
	/**
	 * Retorna a lista de comandos em string.
	 * O responsável pelo BOT pode copiar e colar do console os comandos aceitos pelo programa. 
	 * O valor copiado pode ser informado no BOT Father 
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
	 * Executa para sempre (No linux é conhecido como deamon)
	 */
	public void run() {		
		while(execute());
	}

	/**
	 * Inicia o processamento do fluxo de trabalho
	 * @return
	 */
	private boolean execute() {
		GetUpdates request = new GetUpdates().offset(offset);
		List<Update> updates = bot.execute(request).updates();
		Stream<Update> stream = updates.stream();
		
		stream.forEach(u -> {
			Logger.info("novas mensagem recebidas");
			nextUpdateOffset(u);
			
			//essa thread garante que os usuários não fiquem em uma fila
			Thread processarPalarelo = new Thread(() -> {				
				Logger.info("processamento thread usuario " + u.message().from().id());
				
				switch(routerWorkFlow(u)) {

				case COMMAND: executeWorkFlowCommand(u); break;
				case ACTION: executeWorkFlowAction(u); break;
				case UNKNOWN:			
				default:
					executeWorkFlowUnknown(u);
					break;	
				}
				
			});
			
			pool.execute(processarPalarelo);
		});		
		
		return true;
	}

	/**
	 * Quando o workflow não for detectado pelo manipulador, esse método será executado.
	 * @param u
	 */
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
			
			if (!session.containsKey(CONTA)) {
				
				boolean isCriarConta = (command instanceof CriarContaCommand);
				boolean isStart = (command instanceof StartCommand);
				boolean isAjuda = (command instanceof AjudaCommand);
				
				if (!isCriarConta && !isStart && !isAjuda) {
					throw new IsNotCommandException("Crie uma conta para usar esses comandos");
				}
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
	 * Uma action é iniciada por um comando.
	 * @param u
	 */
	private void executeWorkFlowAction(Update u) {
		SessionManager session = SessionManager.getInstance(u.message().from().id());
		AbstractAction action = session.get(NEXT_ACTION, AbstractAction.class);
		action.execute(bot, u.message());
	}
	
	/**
	 * Detectar para qual fluxo o programa vai ser direcionado (de acordo com ações do usuário no chat)
	 * @param mensagem recebida
	 * @return fluxo que o programa deve tomar
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
	 * @param
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
