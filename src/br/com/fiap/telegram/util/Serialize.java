package br.com.fiap.telegram.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * Classe utilitária que utiliza io para gravar uma classe serializavel em arquivo e recuperar essa classe do arquivo
 * @author Diego.Saouda
 *
 * @param <T> classe genérica de serialize
 */
final public class Serialize<T> {

	private File file;

	/**
	 * Arquivo para armazenar ou recuperar uma classe
	 * @param file arquivo com o caminho para recuperar ou serializar
	 */
	public Serialize(File file) {
		this.file = file;
	}

	/**
	 * Unserialize uma classe em um arquivo para um objeto em memória
	 * @param <T> tipo genérico 
	 * @return objeto em memória
	 */
	@SuppressWarnings({ "hiding", "unchecked" })
	public <T> T unserialize() {
		try {
			FileInputStream fileIn = new FileInputStream(file);
			ObjectInputStream in = new ObjectInputStream(fileIn);
			Object o = in.readObject();	

			in.close();
			fileIn.close();

			return (T) o;

		} catch(ClassNotFoundException | IOException e) {
			throw new RuntimeException(e);
		}
	}

	/**
	 * Serializa um objeto em um arquivo
	 * @param t objeto a ser serializado
	 */
	public void serialize(T t) {
		try {
			FileOutputStream fileOut = new FileOutputStream(file);
			ObjectOutputStream out = new ObjectOutputStream(fileOut);
			out.writeObject(t);
			out.close();
			fileOut.close();
		} catch(IOException e) {
			throw new RuntimeException(e);
		}
	}

}
