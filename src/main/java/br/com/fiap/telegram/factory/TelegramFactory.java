package br.com.fiap.telegram.factory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

import br.com.fiap.telegram.util.Config;

final public class TelegramFactory {	
	private TelegramFactory() {};
	
	public static TelegramBot create() {
		String token = Config.get("telegram.token");
		
		if (token == null || token.isEmpty()) {
			throw new NullPointerException("Token do telegram precisa ser preenchido no arquivo /config.properties");
		}
		
		return TelegramBotAdapter.build(token);
	}

}
