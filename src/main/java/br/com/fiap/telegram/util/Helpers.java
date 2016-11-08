package br.com.fiap.telegram.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Random;

import com.pengrad.telegrambot.model.request.KeyboardButton;
import com.pengrad.telegrambot.model.request.ReplyKeyboardMarkup;

final public class Helpers {
	private static DateTimeFormatter formatterDateTime = DateTimeFormatter.ofPattern("dd/MM/YYYY HH:mm");
	private static DateTimeFormatter formatterDate = DateTimeFormatter.ofPattern("dd/MM/YYYY");
	
	private Helpers() {};
	
	public static String formatarDataHora() {
		return LocalDateTime.now().format(formatterDateTime);		
	}
	
	public static String formatarDataHora(LocalDateTime dateTime) {
		return dateTime.format(formatterDateTime);		
	}
	
	public static String formatarData(LocalDateTime dateTime) {
		return dateTime.format(formatterDate);		
	}
	
	public static String formatarData(LocalDate date) {
		return date.format(formatterDate);		
	}
	
	public static int geradorNumero(int min, int max) {
		Random r = new Random();
		return r.nextInt((max - min) + 1) + min;
	}
	
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
