package br.com.fiap.telegram.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;
/**
 * classe singleton utilitária, com vários métodos auxiliares (helpers)
 * 
 * @author Diego.Saouda
 *
 */
final public class Helpers {
	private static DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
	private static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	
	private Helpers() {};
	
	/**
	 * Formata a data e hora atual no formato brasileiro
	 * @return Data e hora formatada
	 */
	public static String formatarDataHora() {
		return LocalDateTime.now().format(formatterDateTime);		
	}
	
	/**
	 * formata uma data e hora passada para o formato brasileiro
	 * @param  dateTime LocalDateTime que será formatado
	 * @return data e hora formatada
	 */
	public static String formatarDataHora(LocalDateTime dateTime) {
		return dateTime.format(formatterDateTime);		
	}
	
	/**
	 * Formata a visualização de um date time exibindo apenas a data no padrão brasileiro
	 * @param dateTime LocalDateTime que será formatado
	 * @return data formatada
	 */
	public static String formatarData(LocalDateTime dateTime) {
		return dateTime.format(formatterDate);		
	}
	
	/**
	 * Formata uma data para o padrão brasileiro
	 * @param date Date que será formatado
	 * @return data formatada
	 */
	public static String formatarData(LocalDate date) {
		return date.format(formatterDate);		
	}
	
	/**
	 * Gerador de número randomico (usado na geração de uma conta)
	 * @param min valor inicial
	 * @param max valor máximo
	 * @return número gerado
	 */
	public static int geradorNumero(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
	/**
	 * KeyboardMarkup do telegram configurado para usar com a opção de saque ou depósito. 
	 * Os valores são uma representação do dinheiro disponível para saque 
	 * @return botão customizado do telegram
	 */
	public static ReplyKeyboardMarkup getTelegramValoresButton() {
		KeyboardButton op1 = new KeyboardButton("5");
		KeyboardButton op2 = new KeyboardButton("10");
		KeyboardButton op3 = new KeyboardButton("20");
		KeyboardButton op4 = new KeyboardButton("40");
		KeyboardButton op5 = new KeyboardButton("50");
		KeyboardButton op6 = new KeyboardButton("100");
		
		KeyboardButton[] grupo1 = {op1, op2};
		KeyboardButton[] grupo2 = {op3, op4};
		KeyboardButton[] grupo3 = {op5, op6};
		
		ReplyKeyboardMarkup reply = new ReplyKeyboardMarkup(grupo1, grupo2, grupo3);
		reply.oneTimeKeyboard(true);
		
		return reply;
	}
}
