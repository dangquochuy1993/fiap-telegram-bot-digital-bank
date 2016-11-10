package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.ContaPrinter;

/**
 * Classe responsável por criar uma mini estrutura para criação de actions de extrato.
 * @author diego
 *
 */
abstract public class AbstractExtratoAction extends AbstractAction {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected String execute(String router) {
		Conta conta = session.get(CONTA, Conta.class);
		
		bot.execute(new SendMessage(chatId, printer().imprimir(conta)));
		
		session.put(CONTA, conta);
		return null;
	}

	/**
	 * A classe que será responsável por extrato deve implementar esse método. Esse método diz qual é o printer especialista que a classe deve cuidar para exibição do extrato.
	 * @return formatador de impressão será retornado
	 */
	protected abstract ContaPrinter printer();
}