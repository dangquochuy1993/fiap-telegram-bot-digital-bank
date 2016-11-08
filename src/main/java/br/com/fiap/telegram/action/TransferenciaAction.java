package br.com.fiap.telegram.action;

import static br.com.fiap.telegram.util.Keys.CONTA;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.DadosBasicoPrinter;

public class TransferenciaAction extends AbstractAction {
	private static final long serialVersionUID = 1L;

	@Override
	protected String execute(String router) {
		String comando = message.text().toUpperCase().trim();
		
		switch(comando) {
		case "SIM": sim(); break;
		case "N�O": nao(); break;
		default: erro(); break;
		}
		
		return null;
	}
	
	private void sim() {
		Conta conta = session.get(CONTA, Conta.class);
		Conta novaConta = conta.migrar();		
		session.put(CONTA, novaConta);
		
		bot.execute(new SendMessage(chatId, "Sua conta foi migrada com sucesso. Os dados de sua nova conta s�o: " + new DadosBasicoPrinter().imprimir(novaConta)));
		bot.execute(new SendMessage(chatId, "As informa��es de sua conta antiga: " + new DadosBasicoPrinter().imprimir(novaConta.antiga())));
		
	}
	
	private void nao() {
		bot.execute(new SendMessage(chatId, "Voc� optou por cancelar o processo de migra��o."));
	}
	
	private void erro() {
		bot.execute(new SendMessage(chatId, "N�o entendi o seu comando e para sua seguran�a encerramos o processo de migra��o. Caso queira migrar sua conta inicie o comando novamente."));
	}
}
