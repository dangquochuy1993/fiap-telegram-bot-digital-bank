package br.com.fiap.telegram.commands;

import java.util.Arrays;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;
import com.pengrad.telegrambot.model.User;
import com.pengrad.telegrambot.model.request.InlineKeyboardButton;
import com.pengrad.telegrambot.model.request.InlineKeyboardMarkup;
import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ParseMode;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

public class HelpCommand extends AbstractCommand {

	public HelpCommand() {
		super("/help", "Ajuda para uso do bot");
	}

	@Override
	protected void executar(TelegramBot bot, Long chatId, User user, Message message, String[] argumentos) {
		System.out.println(Arrays.toString(argumentos));

		SendMessage send = new SendMessage(chatId, "Estou no start");
		send.parseMode(ParseMode.HTML);

		InlineKeyboardButton ikb1 = new InlineKeyboardButton("ok1");
		ikb1.callbackData("callbackData data teste");
		//ikb1.url("url");
		ikb1.callbackGame("callbackGame");
		//ikb1.switchInlineQuery("switchInlineQuery");
		//ikb1.switchInlineQueryCurrentChat("switchInlineQueryCurrentChat");
		
		InlineKeyboardButton[] keyboards = {ikb1};
		InlineKeyboardMarkup i = new InlineKeyboardMarkup(keyboards);
		
		send.replyMarkup(i);
		bot.execute(send);
	}

}
