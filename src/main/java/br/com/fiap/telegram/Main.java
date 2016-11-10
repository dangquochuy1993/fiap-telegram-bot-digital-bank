package br.com.fiap.telegram;

import br.com.fiap.telegram.command.AdicionarDependenteCommand;
import br.com.fiap.telegram.command.AjudaCommand;
import br.com.fiap.telegram.command.DadosContaCommand;
import br.com.fiap.telegram.command.DepositarCommand;
import br.com.fiap.telegram.command.EmprestimoCommand;
import br.com.fiap.telegram.command.ExtratoCommand;
import br.com.fiap.telegram.command.ExtratoDetalhadoCommand;
import br.com.fiap.telegram.command.ExtratoEmprestimoCommand;
import br.com.fiap.telegram.command.ExtratoSaquesCommand;
import br.com.fiap.telegram.command.ExtratoTarifasCommand;
import br.com.fiap.telegram.command.RemoverDependenteCommand;
import br.com.fiap.telegram.command.SaqueCommand;
import br.com.fiap.telegram.command.TransferenciaCommand;
import br.com.fiap.telegram.handler.TelegramHandler;
import br.com.fiap.telegram.util.Logger;

/**
 * Iniciando a execução do BOT 
 * @author Diego.Saouda
 *
 */
public class Main {

	public static void main(String[] args) {
		TelegramHandler handler = new TelegramHandler();
		handler
		.addCommand(new AdicionarDependenteCommand())
		.addCommand(new RemoverDependenteCommand())
		.addCommand(new DepositarCommand())
		.addCommand(new SaqueCommand())
		.addCommand(new TransferenciaCommand())
		.addCommand(new DadosContaCommand())
		.addCommand(new ExtratoCommand())
		.addCommand(new EmprestimoCommand())
		.addCommand(new ExtratoEmprestimoCommand())
		.addCommand(new ExtratoSaquesCommand())
		.addCommand(new ExtratoTarifasCommand())
		.addCommand(new ExtratoDetalhadoCommand())
		.addCommand(new AjudaCommand(handler.printCommands()));

		Logger.info("iniciando o bot ... ");
		Logger.info("lista dos comandos" + handler.printCommands());
		handler.run();		
	}
}
