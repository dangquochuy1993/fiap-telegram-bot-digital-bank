package br.com.fiap.telegram.model;

public enum TipoTransacao {

	ABERTURA_CONTA("Abertura conta"),
	TRANSFERENCIA("Transferencia conta"),
	EMPRESTIMO("Empr�stimo"),
	ADICIONADO_DEPENDENTE("Dependente adicionado"),
	REMOVIDO_DEPENDENTE("Dependente removido"),
	SAQUE("Saque"),
	TAXA_SAQUE("Taxa saque"),
	TAXA_EXTRATO("Taxa extrato"),
	TAXA_EMPRESTIMO("Taxa empr�stimo"),
	TAXA_EMPRESTIMO_DEVOLUCAO("Devolu��o taxa de empr�stimo"),
	DEPOSITO("Dep�sito");
	
	private String descricao;

	TipoTransacao(String descricao) {
		this.descricao = descricao;		
	}
	
	public String descricao() {
		return descricao;
	}
}
