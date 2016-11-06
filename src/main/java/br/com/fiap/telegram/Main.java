package br.com.fiap.telegram;

import br.com.fiap.telegram.command.DepositarCommand;
import br.com.fiap.telegram.command.ExtratoCommand;
import br.com.fiap.telegram.command.SaqueCommand;
import br.com.fiap.telegram.handler.TelegramHandler;

public class Main {

	public static void main(String[] args) {
		System.out.println("iniciou ...");
		
		new TelegramHandler()
			.addCommand(new DepositarCommand())
			.addCommand(new SaqueCommand())
			.addCommand(new ExtratoCommand())
			.run();		
	}
}
