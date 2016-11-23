package br.com.fiap.telegram.action;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;

import br.com.fiap.telegram.util.Logger;
import br.com.fiap.telegram.util.SessionManager;

/**
 * Classe responsável por gerenciar as ações de um comando.
 * Essa é uma classe genérica que torna a implementação de uma classe especialista mais simples.
 * @author diego
 *
 */
public abstract class AbstractAction {
	protected SessionManager session;
	protected TelegramBot bot;
	protected Message message;
	protected Long chatId;
	private String routerName = "";
	
	/**
	 * Executa a ação solicitada pelo usuário através de um command
	 * @param bot Api de interação com telegram
	 * @param message Mensagem digitada pelo usuário
	 */
	public void execute(TelegramBot bot, Message message) {
		this.bot = bot;
		this.message = message;
		this.chatId = message.chat().id();
		this.session = SessionManager.getInstance(message.from().id());
		
		Logger.info("action=" + this.getClass().getName() + " router=" + routerName);
		
		//registra a próxima rota a ser executada
		String proximaRota = execute(routerName);		
		routerName = proximaRota == null ? "" : proximaRota;
	}
	
	public String getRouterName() {
		return routerName;
	}
	
	/**
	 * Método abstrato que facilita a criação de uma action
	 * @param router informa a rota que a action deve seguir. O papel da rota é rotear/direcionar o fluxo do problema para ser executado uma ação corretamente.
	 * @return o nome de uma rota pode ser retornado. Se isso ocorrer indica ou handler do telegram que a action deve continuar sendo executada após a próxima interação com o usuário. Se null significa não é para continuar a interação.
	 */
	protected abstract String execute(String router);
	
}
