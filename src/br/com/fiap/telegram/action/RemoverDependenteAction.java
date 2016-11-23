package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import java.util.Set;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;

/**
 * Remover um dependente de uma conta
 * @author diego
 *
 */
public class RemoverDependenteAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	private static final String ROUTER_REMOVER = "routerRemover";

	@Override
	protected String execute(String router) {
		switch(router) {
			case ROUTER_REMOVER:
				return routerRemover();
		}
		
		return routerInit();
	}

	/**
	 * Solicita ao usuário que informe o nome do dependente que deseja remover. Junto é enviado um teclado do telegram com as opções de dependentes na qual é só clicar no nome do dependente.
	 * @return Retorna a rota de remover
	 */
	private String routerInit() {
		Conta conta = session.get(CONTA, Conta.class);
		SendMessage send = new SendMessage(chatId, "Opção Remover Dependente.\n\nInforme o nome do dependente, se preferir escolha uma opção abaixo.");
		Set<Cliente> dependentes = conta.getDependentes();
		
		KeyboardButton[][] grupo = new KeyboardButton[dependentes.size()][1];
		
		int i = 0;
		for (Cliente d : dependentes) {
			grupo[i++][0] = new KeyboardButton(d.getNome());
		}
		
		ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(grupo);
		reply.oneTimeKeyboard(true);
		send.replyMarkup(reply);
		bot.execute(send);
		
		return ROUTER_REMOVER;
	}

	/**
	 * Executa a remoção do dependente informada pela rota init
	 * @return Se dependente removido null será retornado para encerrar a action, caso contrário retorna a rota REMOVER para tentar realizar o procedimento novamente.
	 */
	private String routerRemover() {
		String nome = message.text();
		
		Conta conta = session.get(CONTA, Conta.class);
		if (conta.removerDependente(new Cliente(nome))) {
			session.put(CONTA, conta);
			bot.execute(new SendMessage(chatId, "Dependente " + nome + " excluído com sucesso."));
			return null;
		} else {
			bot.execute(new SendMessage(chatId, "Não encontramos o dependente " + nome + ". Tente novamente."));
			return ROUTER_REMOVER;
		}
	}

}
