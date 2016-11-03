package br.com.fiap.telegram.actions;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;

public abstract class AbstractActions {

	private String nome;

	protected AbstractActions() {
		nome = this.getClass().getSimpleName();
	}
	
	public String getNome() {
		return nome;
	}
	

	public abstract void executarButton(TelegramBot bot, Message message, CallbackData data);	
	public abstract void executarInput(TelegramBot bot, Message messageButton, Message messageInput, CallbackData data);
	
}
