package br.com.fiap.telegram.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

final public class Config {

	private static Properties props;
	
	private Config() {}	

	static {
		InputStream resource = Config.class.getResourceAsStream("/config.properties");
		props = new Properties();

		try {
			props.load(resource);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String get(String key) {
		return props.getProperty(key);
	}

}
