package br.com.fiap.telegram.model;

public enum TipoTransacao {

	ABERTURA_CONTA("Abertura Conta"),
	ADICIONADO_DEPENDENTE("Dependente adicionado"),
	REMOVIDO_DEPENDENTE("Dependente removido"),
	SAQUE("Saque"),
	TAXA_SAQUE("Taxa saque"),
	TAXA_EXTRATO("Taxa extrato"),
	DEPOSITO("Depósito");
	
	private String descricao;

	TipoTransacao(String descricao) {
		this.descricao = descricao;		
	}
	
	public String descricao() {
		return descricao;
	}
}
