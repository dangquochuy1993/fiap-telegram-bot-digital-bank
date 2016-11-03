package br.com.fiap.telegram.actions;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.request.EditMessageText;
import com.pengrad.telegrambot.request.SendMessage;

public class CriarContaAction extends AbstractActions {

	private static final int FLOW_EDITAR_NOME = 1;
	private static final int FLOW_EDITAR_IDADE = 2;
	
	public void executar(TelegramBot bot, Message message) {
		SendMessage send = new SendMessage(message.chat().id(), "<b>Nome: </b>\n<b>Idade: </b>");
		send.parseMode(ParseMode.HTML);
		send.replyMarkup(btnActions());
		bot.execute(send);
	}

	public void executarButton(TelegramBot bot, Message message, CallbackData data) {
		System.out.println(data);
		
		EditMessageText edit = null;
		
		switch(data.getFlow()) {
			case FLOW_EDITAR_NOME: 
				//edit = new EditMessageText(message.chat().id(), message.messageId(), "<b>Nome: </b>Diego\n<b>Idade: </b>");				
				bot.execute(new SendMessage(message.chat().id(), "Digite seu nome"));
				
				break;
				
			case FLOW_EDITAR_IDADE:
				//edit = new EditMessageText(message.chat().id(), message.messageId(), "<b>Nome: </b>Diego\n<b>Idade: </b>28");
				bot.execute(new SendMessage(message.chat().id(), "Digite sua idade"));
				break;
		}
		
				
		//edit.parseMode(ParseMode.HTML);
		//edit.replyMarkup(btnActions());
		//bot.execute(edit);
	}
	
	public void executarInput(TelegramBot bot, Message messageButton, Message messageInput, CallbackData data) {
		System.out.println(data);
		
		EditMessageText edit = null;
		
		switch(data.getFlow()) {
			case FLOW_EDITAR_NOME: 
				edit = new EditMessageText(messageButton.chat().id(), messageButton.messageId(), "<b>Nome: </b>" + messageInput.text());				
				
				break;
				
			case FLOW_EDITAR_IDADE:
				//edit = new EditMessageText(message.chat().id(), message.messageId(), "<b>Nome: </b>Diego\n<b>Idade: </b>28");
				//bot.execute(new SendMessage(message.chat().id(), "Digite sua idade"));
				break;
		}
		
				
		edit.parseMode(ParseMode.HTML);
		edit.replyMarkup(btnActions());
		bot.execute(edit);
	}
	
	
	
	/*
	public void executar(TelegramBot bot, CallbackQuery query, CallbackData data) {
		System.out.println(data);
		
		EditMessageText edit = null;
		
		switch(data.getFlow()) {
			case FLOW_EDITAR_NOME: 
				edit = new EditMessageText(query.message().chat().id(), query.message().messageId(), "<b>Nome: </b>Diego\n<b>Idade: </b>");		
				break;
				
			case FLOW_EDITAR_IDADE:
				edit = new EditMessageText(query.message().chat().id(), query.message().messageId(), "<b>Nome: </b>Diego\n<b>Idade: </b>28");				
				break;
		}
		
				
		edit.parseMode(ParseMode.HTML);
		edit.replyMarkup(btnActions());
		bot.execute(edit);
	}
	*/
	
	private InlineKeyboardMarkup btnActions() {
		InlineKeyboardButton btnEditNome = new InlineKeyboardButton("Editar Nome");
		btnEditNome.callbackData(new CallbackData(getNome(), FLOW_EDITAR_NOME).toJson());
		
		InlineKeyboardButton btnEditIdade = new InlineKeyboardButton("Editar Idade");
		btnEditIdade.callbackData(new CallbackData(getNome(), FLOW_EDITAR_IDADE).toJson());
		
		InlineKeyboardButton[] grupoBotoes = { btnEditNome, btnEditIdade };
		return new InlineKeyboardMarkup(grupoBotoes);
	}
	
}
