package br.com.fiap.telegram.model;

/**
 * Tipo das transações de uma conta
 * @author Diego.Saouda
 *
 */
public enum TipoTransacao {

	ABERTURA_CONTA("Abertura conta"),
	TRANSFERENCIA("Transferencia conta"),
	EMPRESTIMO("Empréstimo"),
	ADICIONADO_DEPENDENTE("Dependente adicionado"),
	REMOVIDO_DEPENDENTE("Dependente removido"),
	SAQUE("Saque"),
	TAXA_SAQUE("Taxa saque"),
	TAXA_EXTRATO("Taxa extrato"),
	TAXA_EMPRESTIMO("Taxa empréstimo"),
	TAXA_EMPRESTIMO_DEVOLUCAO("Devolução taxa de empréstimo"),
	DEPOSITO("Depósito");
	
	private String descricao;

	TipoTransacao(String descricao) {
		this.descricao = descricao;		
	}
	
	public String descricao() {
		return descricao;
	}
}
