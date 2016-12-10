package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Armazena todos os movimentos ocorrido em uma conta (Collection de transações)
 * @author Diego.Saouda
 *
 */
public class HistoricoTransacoes implements Serializable {
	private static final long serialVersionUID = 1L;
	
	private List<Transacao> transacoes = new ArrayList<>();
	
	/**
	 * Adicionar uma nova transação
	 * @param tipo tipo transação
	 * @param valor que será registrado na transação
	 * @param saldo que será registrado na transação
	 * @return adicionar uma nova transação
	 */
	public HistoricoTransacoes adicionar(TipoTransacao tipo, BigDecimal valor, BigDecimal saldo) {
		transacoes.add(new Transacao(LocalDateTime.now(), tipo, valor, saldo));
		return this;
	}

	public HistoricoTransacoes adicionar(TipoTransacao tipo) {
		adicionar(tipo, new BigDecimal(0), new BigDecimal(0));
		return this;
	}
	
	/**
	 * Retorna as transações, mas imutável para o usuário não manipular essa informação fora de uma conta
	 * @return retorna as transações imutáveis
	 */
	public List<Transacao> getTransacoes() {
		return Collections.unmodifiableList(transacoes);
	}
	
}
