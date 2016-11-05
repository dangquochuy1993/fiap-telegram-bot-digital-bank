package br.com.fiap.telegram.commands;

import java.io.Serializable;
import java.util.Arrays;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.Update;
import com.pengrad.telegrambot.model.User;

import br.com.fiap.telegram.exceptions.NaoEhUmComandoException;

public abstract class AbstractCommand implements Serializable {
	
	private String nome;
	private String descricao;

	public AbstractCommand(String nome, String descricao) {
		setNome(nome);
		this.descricao = descricao;
	}
	
	public static boolean isCommand(String texto) {
		return texto.matches("/[a-z]{1,32}.*");
	}
	
	public static String extrairNomeComando(String texto) throws NaoEhUmComandoException {
		
		if (!isCommand(texto)) {
			throw new NaoEhUmComandoException(texto);
		}
		
		
		return texto.split("\\s")[0];
	}
	
	private static String[] extrairArgumentos(String texto) {
		String[] argumentos = texto.split("\\s");
		return Arrays.copyOfRange(argumentos, 1, argumentos.length);
	}
	
	private void setNome(String nome) {
		
		if (nome == null || !isCommand(nome)) {
			throw new IllegalArgumentException("nome do comando não é válido. Deve iniciar com / e conter apenas caracteres de a-z");
		}
		
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	public String getDescricao() {
		return descricao;
	}

	public void onUpdateReceived(TelegramBot bot, Update update) {
		Message message = update.message();
		Long chatId = message.chat().id();
		User user = message.from();
		
		String[] argumentos = extrairArgumentos(message.text());
		
		
		executar(bot, chatId, user, message, argumentos);
	}

	protected  abstract void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos);
}
