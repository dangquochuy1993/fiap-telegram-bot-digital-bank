package br.com.fiap.telegram.model;

import java.io.Serializable;

public class Cliente implements Serializable {

	private String nome;
	
	public Cliente(String nome) {
		this.nome = nome;		
	}
	
	public Cliente setNome(String nome) {
		this.nome = nome;
		return this;
	}
	
	public String getNome() {
		return nome;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nome == null) ? 0 : nome.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Cliente other = (Cliente) obj;
		if (nome == null) {
			if (other.nome != null)
				return false;
		} else if (!nome.equals(other.nome))
			return false;
		return true;
	}

	@Override
	public String toString() {
		return "Cliente [nome=" + nome + "]";
	}
}
