package br.com.fiap.telegram.exception;

/**
 * Quando uma mensagem de usuário não for um comando essa exception será lançada.
 * @author Diego.Saouda
 *
 */
public class IsNotCommandException extends Exception {
	private static final long serialVersionUID = 1L;
	
	public IsNotCommandException(String texto) {
		super(texto);
	}

}
