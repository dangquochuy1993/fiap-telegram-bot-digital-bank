package br.com.fiap.telegram;

import br.com.fiap.telegram.actions.CriarContaAction;
import br.com.fiap.telegram.commands.CriarContaCommand;
import br.com.fiap.telegram.commands.DepositarCommand;
import br.com.fiap.telegram.commands.ExtratoCommand;
import br.com.fiap.telegram.commands.MenuCommand;
import br.com.fiap.telegram.commands.SaqueCommand;
import br.com.fiap.telegram.commands.StartCommand;
import br.com.fiap.telegram.handler.TelegramHandler;

public class Main {

	public static void main(String[] args) {
		System.out.println("iniciou ...");
		
		new TelegramHandler()
			.addCommand(new MenuCommand())
			.addCommand(new StartCommand())
			.addCommand(new CriarContaCommand())
			.addCommand(new DepositarCommand())
			.addCommand(new SaqueCommand())
			.addCommand(new ExtratoCommand())
						
			.addAction(new CriarContaAction())			
			.run();		
	}
}
