package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.NEXT_ACTION;
import static br.com.fiap.telegram.util.Keys.ROUTER;

import java.io.Serializable;

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
public abstract class AbstractAction implements Serializable {
	private static final long serialVersionUID = 1L;
	
	protected transient SessionManager session;
	protected transient TelegramBot bot;
	protected transient Message message;
	protected transient Long chatId;
	
	/**
	 * Executa a ação solicitada pelo usuário através de um command
	 * @param bot Api de interação com telegram
	 * @param message Mensagem digitada pelo usuário
	 * @return false significa que o fluxo foi finalizado e true que o fluxo de action foi executado (ou será executado)
	 */
	public boolean execute(TelegramBot bot, Message message) {
		this.bot = bot;
		this.message = message;
		this.chatId = message.chat().id();
		this.session = SessionManager.getInstance(message.from().id());
		
		String routerName = session.get(ROUTER, String.class);
		
		if (routerName == null) {
			routerName = "";
		}
		
		Logger.info("action=" + this.getClass().getName() + " router=" + routerName);
		
		String nextRouter = execute(routerName);
		
		if (nextRouter == null) {
			session.remove(ROUTER);
			session.remove(NEXT_ACTION);
			return false;
		} else {
			session.put(ROUTER, nextRouter);
			return true;
		}
	}

	/**
	 * Método abstrato que facilita a criação de uma action
	 * @param router informa a rota que a action deve seguir. O papel da rota é rotear/direcionar o fluxo do problema para ser executado uma ação corretamente.
	 * @return o nome de uma rota pode ser retornado. Se isso ocorrer indica ou handler do telegram que a action deve continuar sendo executada após a próxima interação com o usuário. Se null significa não é para continuar a interação.
	 */
	protected abstract String execute(String router);
	
}
