package br.com.fiap.telegram.command;

import java.util.Arrays;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import br.com.fiap.telegram.action.AbstractAction;
import br.com.fiap.telegram.exception.IsNotCommandException;

public abstract class AbstractCommand {
	
	private String name;
	private String description;

	public AbstractCommand(String name, String description) {
		setName(name);
		this.description = description;
	}
	
	public static boolean isCommand(String texto) {
		return texto.matches("/[a-z]{1,32}.*");
	}
	
	public static String extractCommandName(String texto) throws IsNotCommandException {
		
		if (!isCommand(texto)) {
			throw new IsNotCommandException(texto);
		}
		
		
		return texto.split("\\s")[0];
	}
	
	private static String[] extractArguments(String texto) {
		String[] argumentos = texto.split("\\s");
		return Arrays.copyOfRange(argumentos, 1, argumentos.length);
	}
	
	private void setName(String name) {
		
		if (name == null || !isCommand(name)) {
			throw new IllegalArgumentException("nome do comando não é válido. Deve iniciar com / e conter apenas caracteres de a-z");
		}
		
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public String getDescription() {
		return description;
	}

	public AbstractAction onUpdateReceived(TelegramBot bot, Update update) {
		Message message = update.message();
		Long chatId = message.chat().id();
		User user = message.from();
		
		String[] argumentos = extractArguments(message.text());
		
		
		return execute(bot, chatId, user, message, argumentos);
	}

	protected  abstract AbstractAction execute(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos);
}
