package br.com.fiap.telegram;

import java.math.BigDecimal;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.model.Conta;

public class TesteConta {

	public static void main(String[] args) {
		Cliente diego = new Cliente("Diego Henrique Sousa Saouda");
		Cliente aline = new Cliente("Aline Lacerda Saouda");
		
		Conta conta = new Conta(diego, new BigDecimal("100.00"));
		conta.adicionarDepentente(aline);		
		System.out.println(conta);
		
		conta.removerDependente(aline);		
		System.out.println(conta);
		
		conta.depositar(new BigDecimal(20));
		System.out.println(conta);
		
		conta.saque(new BigDecimal(20));
		System.out.println(conta);
		
		conta.saque(new BigDecimal(91));
		System.out.println(conta);
		
		System.out.println(conta.extrato());
	}
	
}
