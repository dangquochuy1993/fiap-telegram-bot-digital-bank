package br.com.fiap.telegram;

import java.math.BigDecimal;

public class StringToBigDecimalTest {

	public static void main(String[] args) {
		
		String valor = "100.50";
		
		BigDecimal bigDecimal = new BigDecimal(valor);
		System.out.println(bigDecimal);
		
	}
	
}
