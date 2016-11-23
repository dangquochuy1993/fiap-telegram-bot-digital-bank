package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.DadosBasicoPrinter;

/**
 * Action responsável por controlar o fluxo de transferência da conta
 * @author diego
 *
 */
public class TransferenciaAction extends AbstractAction {
	private static final String NAO = "NÃO";
	private static final String SIM = "SIM";

	@Override
	protected String execute(String router) {
		String comando = message.text().toUpperCase().trim();
		
		switch(comando) {
		case SIM: sim(); break;
		case NAO: nao(); break;
		default: erro(); break;
		}
		
		return null;
	}
	
	/**
	 * Se o usuário informar sim essa opção será executada. A conta será migrada.
	 */
	private void sim() {
		Conta conta = session.get(CONTA, Conta.class);
		Conta novaConta = conta.migrar();		
		session.put(CONTA, novaConta);
		
		bot.execute(new SendMessage(chatId, "Sua conta foi migrada com sucesso. Os dados de sua nova conta são: " + new DadosBasicoPrinter().imprimir(novaConta)));
		bot.execute(new SendMessage(chatId, "As informações de sua conta antiga: " + new DadosBasicoPrinter().imprimir(novaConta.antiga())));
		
	}
	
	/**
	 * Em caso de escolher não essa opção será executada e a migração será encerrada.
	 */
	private void nao() {
		bot.execute(new SendMessage(chatId, "Você optou por cancelar o processo de migração."));
	}
	
	/**
	 * Caso o usuário escolha qualquer coisa diferente de sim ou não essa ação de erro será executada.
	 */
	private void erro() {
		bot.execute(new SendMessage(chatId, "Não entendi o seu comando e para sua segurança encerramos o processo de migração. Caso queira migrar sua conta inicie o comando novamente."));
	}
}
