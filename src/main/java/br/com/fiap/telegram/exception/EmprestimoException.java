package br.com.fiap.telegram.exception;

/**
 * Exception quando um emprestimo falhar
 * @author Diego.Saouda
 *
 */
public class EmprestimoException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	
	public EmprestimoException(String message) {
		super(message);
	}
}
