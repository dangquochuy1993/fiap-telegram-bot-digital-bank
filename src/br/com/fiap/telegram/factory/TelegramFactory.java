package br.com.fiap.telegram.factory;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.TelegramBotAdapter;

import br.com.fiap.telegram.util.Config;

/**
 * Design Pattener Factory utilizado para criação da classe TelgramBot.
 * Essa classe necessita de um token. Para deixar a inserção do token flexível, o token foi colocado em um
 * arquivo properties. Essa classe tem a responsabilidade de montar esse objecto da API do telegram.
 *   
 * @author Diego.Saouda
 *
 */
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
