package br.com.fiap.telegram.printer;

import java.math.MathContext;

import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.model.Emprestimo;
import br.com.fiap.telegram.util.Helpers;

public class EmprestimoPrinter implements ContaPrinter {

	@Override
	public String imprimir(Conta conta) {
		Emprestimo emprestimo = conta.getEmprestimo();
		
		return "Extrato empréstimo" + 
			"\nValor por mês: " + emprestimo.getValorMes().round(MathContext.DECIMAL32) +
			"\nJuros ao mês: " + emprestimo.getJurosMes().round(MathContext.DECIMAL32) +			
			"\nTotal a ser pago " + emprestimo.getValorTotal().round(MathContext.DECIMAL32) +			
			" até a data " + Helpers.formatarData(emprestimo.getPrazo());
	}

}
