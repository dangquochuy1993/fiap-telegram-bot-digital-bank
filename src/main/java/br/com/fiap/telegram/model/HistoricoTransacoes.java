package br.com.fiap.telegram.model;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class HistoricoTransacoes implements Serializable {
	private List<Transacao> transacoes = new ArrayList<>();
	
	public HistoricoTransacoes adicionar(TipoTransacao tipo, BigDecimal valor, BigDecimal saldo) {
		transacoes.add(new Transacao(LocalDateTime.now(), tipo, valor, saldo));
		return this;
	}

	public HistoricoTransacoes adicionar(TipoTransacao tipo) {
		adicionar(tipo, new BigDecimal(0), new BigDecimal(0));
		return this;
	}
	
	public List<Transacao> getTransacoes() {
		return Collections.unmodifiableList(transacoes);
	}
	
}
