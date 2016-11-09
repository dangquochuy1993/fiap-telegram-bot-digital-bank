package br.com.fiap.telegram.action;

import java.math.BigDecimal;

import com.pengrad.telegrambot.request.SendMessage;

import br.com.fiap.telegram.exception.EmprestimoException;
import br.com.fiap.telegram.exception.PrazoPagamentoException;
import br.com.fiap.telegram.exception.SaldoInsuficienteException;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.DadosBasicoPrinter;
import br.com.fiap.telegram.printer.EmprestimoPrinter;
import br.com.fiap.telegram.util.Keys;

public class EmprestimoAction extends AbstractAction {
	private static final String ROUTER_VALOR = "routerValor";

	private static final long serialVersionUID = 1L;
	
	@Override
	protected String execute(String router) {
		
		switch (router) {
		case ROUTER_VALOR:
			return routerValor();
			
		default:	
		case "routerPrazo":
			return routerPrazo();
		}
	}

	private String routerPrazo() {
		String prazo = message.text();
		session.put(Keys.EMPRESTIMO_PRAZO, prazo);
		
		bot.execute(new SendMessage(chatId, "Agora informe o valor do emprestimo.\nFormato deve ser 0.00"));
		
		return ROUTER_VALOR;
	}

	private String routerValor() {
		BigDecimal valor;
		
		try {
			valor = new BigDecimal(message.text());
		} catch (NumberFormatException e) {
			bot.execute(new SendMessage(chatId, "Não consegui entender o valor informado, tente novamente por favor. Informe no formato 0.00"));
			return ROUTER_VALOR;
		}
		
		String prazo = session.get(Keys.EMPRESTIMO_PRAZO, String.class);
		Conta conta = session.get(Keys.CONTA, Conta.class);
		
		try {
			conta.emprestimo(valor, prazo);
			bot.execute(new SendMessage(chatId, String.format("Empréstimo realizado com sucesso\n\n %s \n\n %s", new EmprestimoPrinter().imprimir(conta), new DadosBasicoPrinter().imprimir(conta))));
			
			session.put(Keys.CONTA, conta);
			session.remove(Keys.EMPRESTIMO_PRAZO);
			
		} catch (EmprestimoException | SaldoInsuficienteException | PrazoPagamentoException  e) {
			bot.execute(new SendMessage(chatId, e.getMessage()));
		} 
			
		return null;
	}

}
