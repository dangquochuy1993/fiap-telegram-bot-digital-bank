package br.com.fiap.telegram.printer;

import br.com.fiap.telegram.model.Conta;

/**
 * Interface responsável por dizer quem é a classe que imprime dados de uma conta
 * @author Diego.Saouda
 *
 */
public interface ContaPrinter {

	public String imprimir(Conta conta);
	
}
