package br.com.fiap.telegram.exception;

/**
 * Exception responsável por cuidar das exceções do prazo de pagamento de um empréstimo
 * @author Diego.Saouda
 *
 */
public class PrazoPagamentoException extends RuntimeException {
	private static final long serialVersionUID = 1L;

	public PrazoPagamentoException(String message) {
		super(message);
	}

}
