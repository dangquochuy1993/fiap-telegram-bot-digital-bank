package br.com.fiap.telegram.util;

import java.time.LocalDateTime;

final public class Logger {

	private Logger() {}
	
	public static void info(String message) {
		log("info", message);
	}
	
	public static void error(String message) {
		log("error", message);
	}
	
	public static void log(String tipo, String message) {
		System.out.println(LocalDateTime.now() + " [" + tipo + "] " + message);
	}
	
}
