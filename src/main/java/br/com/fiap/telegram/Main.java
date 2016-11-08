package br.com.fiap.telegram;

import br.com.fiap.telegram.command.AdicionarDependenteCommand;
import br.com.fiap.telegram.command.AjudaCommand;
import br.com.fiap.telegram.command.DadosContaCommand;
import br.com.fiap.telegram.command.DepositarCommand;
import br.com.fiap.telegram.command.ExtratoCommand;
import br.com.fiap.telegram.command.ExtratoDetalhadoCommand;
import br.com.fiap.telegram.command.ExtratoSaquesCommand;
import br.com.fiap.telegram.command.ExtratoTarifasCommand;
import br.com.fiap.telegram.command.RemoverDependenteCommand;
import br.com.fiap.telegram.command.SaqueCommand;
import br.com.fiap.telegram.handler.TelegramHandler;

public class Main {

	public static void main(String[] args) {
		TelegramHandler handler = new TelegramHandler();
		handler
		.addCommand(new AdicionarDependenteCommand())
		.addCommand(new RemoverDependenteCommand())
		.addCommand(new DepositarCommand())
		.addCommand(new SaqueCommand())
		.addCommand(new DadosContaCommand())
		.addCommand(new ExtratoCommand())
		.addCommand(new ExtratoSaquesCommand())
		.addCommand(new ExtratoTarifasCommand())
		.addCommand(new ExtratoDetalhadoCommand())
		.addCommand(new AjudaCommand(handler.printCommands()));

		System.out.println("iniciando o bot ... ");
		System.out.println(handler.printCommands());

		handler.run();		
	}
}
