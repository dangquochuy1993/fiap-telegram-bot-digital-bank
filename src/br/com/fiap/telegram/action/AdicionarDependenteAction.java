package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;

/**
 * Ação responsável por adicionar um dependente a uma conta
 * @author diego
 *
 */
public class AdicionarDependenteAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected String execute(String router) {
		
		Conta conta = session.get(CONTA, Conta.class);
		Cliente dependente = new Cliente(message.text());
		conta.adicionarDepentente(dependente);
		
		session.put(CONTA, conta);		
		bot.execute(new SendMessage(chatId, "Novo depentente ("+ dependente.getNome() +") adicionado com Sucesso"));
		
		return null;
	}

}
