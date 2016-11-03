package br.com.fiap.telegram;

import br.com.fiap.telegram.commands.HelpCommand;
import br.com.fiap.telegram.commands.StartCommand;
import br.com.fiap.telegram.handler.TelegramHandler;

public class Main {

	public static void main(String[] args) {
		System.out.println("iniciou ...");
		
		new TelegramHandler()
			.addCommand(new StartCommand())
			.addCommand(new HelpCommand())
			.run();
		
	}
}
