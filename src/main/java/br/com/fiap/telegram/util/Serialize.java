package br.com.fiap.telegram.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

final public class Serialize<T> {

	private File file;

	public Serialize(File file) {
		this.file = file;
	}

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
