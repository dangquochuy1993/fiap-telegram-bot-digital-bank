package br.com.fiap.telegram;

import java.util.HashMap;
import java.util.Map;

import com.google.gson.internal.Primitives;

final public class SessionManager {

	private SessionManager() {}
	private static Map<String, Object> storage = new HashMap<>();

	public static void put(String key, Object o) {
		storage.put(key, o);
	}

	public static <T> T get(String key, Class<T> classOfT) {
		Object object = storage.get(key);
		return Primitives.wrap(classOfT).cast(object);
	}
	
	public static Object get(String key) {
		return storage.get(key);
	}
	
	public static Object remove(String key) {
		return storage.remove(key);
	}
	
	public static boolean containsKey(String key) {
		return storage.containsKey(key);
	}
	
	public static String show() {
		return storage.toString();
	}
}
