package br.com.fia.telegram.test.util;

import br.com.fiap.telegram.model.Cliente;
import br.com.fiap.telegram.util.SessionManager;

public class SessionManagerTest {
	
	public static void main(String[] args) {
		SessionManager session = SessionManager.getInstance(new Integer(10));
		//session.put("cliente", new Cliente("Diego Saouda"));
		
		Cliente cliente = session.get("cliente", Cliente.class);
		System.out.println(cliente.getNome());
		System.out.println(cliente.setNome("Diego Henrique Sousa Saouda"));
		
		session.save();
	}
}
