package br.com.fiap.telegram;

import java.math.BigDecimal;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;
import br.com.fiap.telegram.printer.DadosBasicoComDependentesPrinter;
import br.com.fiap.telegram.printer.ExtratoSaquesPrinter;
import br.com.fiap.telegram.printer.ExtratoTarifasPrinter;

public class ExtratoSaque {
	public static void main(String[] args) {
		Conta conta = new Conta(new Cliente("Diego Saouda"), new BigDecimal(100));
		conta.depositar(new BigDecimal(10));
		conta.saque(new BigDecimal(5));
		conta.saque(new BigDecimal(10));
		conta.depositar(new BigDecimal(20));
		conta.saque(new BigDecimal(20));		
		conta.depositar(new BigDecimal(20));
		
		System.out.println(new ExtratoSaquesPrinter().imprimir(conta));
		System.out.println("\n\n");
		System.out.println(new ExtratoTarifasPrinter().imprimir(conta));		
		System.out.println("\n\n");
		System.out.println(new DadosBasicoComDependentesPrinter().imprimir(conta));
		
	}
}
