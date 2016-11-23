package br.com.fiap.telegram.command;

import java.util.Arrays;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.exception.IsNotCommandException;
import br.com.fiap.telegram.util.Logger;

/**
 * Essa é uma super classe responsável por controlar a execução de comandos no telegram.
 * Vários métodos auxiliares foram criados para serem usados nas classes especializadas pelo comando em si.
 * @author Diego.Saouda
 *
 */
public abstract class AbstractCommand {
	
	/**
	 * Nome do comando
	 */
	private String name;
	
	/**
	 * Descrição do que o comando pode fazer
	 */
	private String description;

	public AbstractCommand(String name, String description) {
		setName(name);
		this.description = description;
	}
	
	/**
	 * Verifica se uma determinada mensagem é um comando
	 * @param texto Mensagem a ser verificada
	 * @return
	 */
	public static boolean isCommand(String texto) {
		return texto.matches("/[a-z]{1,32}.*");
	}
	
	/**
	 * Extrair o nome do comando
	 * @param texto usado para extrair o comando digitado pelo usuário
	 * @return nome do comando extraído na mensagem
	 * @throws IsNotCommandException se o texto não for um comando válido
	 */
	public static String extractCommandName(String texto) throws IsNotCommandException {
		
		if (!isCommand(texto)) {
			throw new IsNotCommandException(texto);
		}
		
		
		return texto.split("\\s")[0];
	}
	
	/**
	 * Extrair os argumentos de um comando
	 * @param texto
	 * @return
	 */
	private static String[] extractArguments(String texto) {
		String[] argumentos = texto.split("\\s");
		return Arrays.copyOfRange(argumentos, 1, argumentos.length);
	}
	
	/**
	 * Informa o nome do comando
	 * @param name
	 */
	private void setName(String name) {
		
		if (name == null || !isCommand(name)) {
			throw new IllegalArgumentException("nome do comando não é válido. Deve iniciar com / e conter apenas caracteres de a-z");
		}
		
		this.name = name;
	}
	
	/**
	 * Retorna o comando
	 * @return
	 */
	public String getName() {
		return name;
	}
	
	/**
	 * Retorna a descrição do comando
	 * @return
	 */
	public String getDescription() {
		return description;
	}

	/**
	 * Toda mensagem enviada pelo usuário será recebida por esse método, que é responsável 
	 * por fazer o roteamento dos comandos. Esse método prepara argumentos para serem utilizados em classes especialistas.
	 * @param bot API telegram bot
	 * @param update Mensagem recebida do telegram
	 * @return action que dará continuidade a execução do comando
	 */
	public AbstractAction onUpdateReceived(TelegramBot bot, Update update) {
		Logger.info("command=" + this.getClass().getName());
		
		Message message = update.message();
		Long chatId = message.chat().id();
		User user = message.from();
		
		String[] argumentos = extractArguments(message.text());
		
		
		return execute(bot, chatId, user, message, argumentos);
	}

	/**
	 * Esse é um alias que basicamente extrai algumas informações pelo método onUpdateReceived e repassa para comandos especialistas já parseado.
	 * @param bot API telegram
	 * @param chatId identificador do chat que o usuário está interagindo
	 * @param user que está interagindo com o bot
	 * @param message digitada pelo usuário
	 * @param argumentos do comando
	 * @return ação a ser executada
	 */
	protected  abstract AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos);
}
