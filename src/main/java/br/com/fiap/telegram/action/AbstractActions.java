package br.com.fiap.telegram.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;

import br.com.fiap.telegram.util.Router;

public abstract class AbstractActions {

	private String nome;

	protected AbstractActions() {
		nome = this.getClass().getSimpleName();
	}
	
	public String getNome() {
		return nome;
	}
	

	public abstract void executarButton(TelegramBot bot, Message message, Router data);	
	public abstract void executarInput(TelegramBot bot, Message messageButton, Message messageInput, Router data);
	
}
