package br.com.fiap.telegram.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Classe singleton responsável por carregar o arquivo de configuração
 * @author Diego.Saouda
 *
 */
final public class Config {

	private static Properties props;
	
	private Config() {}	

	/**
	 * Carregar o arquivo de configuração para memória
	 */
	static {
		InputStream resource = Config.class.getResourceAsStream("/config.properties");
		props = new Properties();

		try {
			props.load(resource);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Ler uma propriedade de um arquivo
	 * @param key nome da propriedade
	 * @return valor da propriedade
	 */
	public static String get(String key) {
		return props.getProperty(key);
	}

}
