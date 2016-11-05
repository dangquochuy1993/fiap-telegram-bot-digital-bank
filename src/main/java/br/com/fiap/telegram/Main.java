package br.com.fiap.telegram;

import br.com.fiap.telegram.action.CriarContaAction;
import br.com.fiap.telegram.command.CriarContaCommand;
import br.com.fiap.telegram.command.DepositarCommand;
import br.com.fiap.telegram.command.ExtratoCommand;
import br.com.fiap.telegram.command.MenuCommand;
import br.com.fiap.telegram.command.SaqueCommand;
import br.com.fiap.telegram.command.StartCommand;
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
