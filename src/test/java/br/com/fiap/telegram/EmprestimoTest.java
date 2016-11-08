package br.com.fiap.telegram;

import java.math.BigDecimal;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.EmprestimoPrinter;

public class EmprestimoTest {

	public static void main(String[] args) {		
		Conta conta = new Conta(new Cliente("Diego Saouda"), new BigDecimal("2000"));
		
		conta.emprestimo(new BigDecimal("40000"), "18 mes");
		
		System.out.println(new EmprestimoPrinter().imprimir(conta));
		
	}
}
