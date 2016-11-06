package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.ROUTER;
import static br.com.fiap.telegram.util.Keys.NEXT_ACTION;

import java.io.Serializable;

import com.pengrad.telegrambot.TelegramBot;
import com.pengrad.telegrambot.model.Message;

import br.com.fiap.telegram.util.RouterAction;
import br.com.fiap.telegram.util.SessionManager;

public abstract class AbstractAction implements Serializable {
	
	//não serializar
	protected transient SessionManager session;
	protected transient TelegramBot bot;
	protected transient Message message;
	protected transient Long chatId;
	
	public boolean execute(TelegramBot bot, Message message) {
		this.bot = bot;
		this.message = message;
		this.chatId = message.chat().id();
		this.session = SessionManager.getInstance(message.from().id());
		
		String routerName = "";
		RouterAction router = session.get(ROUTER, RouterAction.class);
		
		if (router != null) {
			routerName = router.getAction();
		}
		
		String nextRouter = execute(routerName);
		
		if (nextRouter == null) {
			session.remove(ROUTER);
			session.remove(NEXT_ACTION);
			return false;
		} else {
			session.put(ROUTER, new RouterAction(nextRouter));
			return true;
		}
	}

	protected abstract String execute(String router);
	
}
