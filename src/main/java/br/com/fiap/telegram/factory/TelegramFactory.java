package br.com.fiap.telegram.factory;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

abstract public class TelegramFactory {

	private static Properties props;
	
	static {
		InputStream resource = TelegramFactory.class.getResourceAsStream("/config.properties");
		props = new Properties();

		try {
			props.load(resource);
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static TelegramBot create() {
		String token = props.getProperty("telegram.token");
		
		if (token == null || token.isEmpty()) {
			throw new NullPointerException("Token do telegram precisa ser preenchido no arquivo /config.properties");
		}
		
		return TelegramBotAdapter.build(token);
	}

}
