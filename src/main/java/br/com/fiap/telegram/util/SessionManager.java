package br.com.fiap.telegram.util;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import com.google.gson.internal.Primitives;

final public class SessionManager implements Serializable {
	private static final long serialVersionUID = -2754015033295421874L;

	private Map<String, Object> storage = new HashMap<>();
	private static Path sessionPath = Paths.get("data/session");
	private Integer sessionId;
	
	private SessionManager(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public static SessionManager getInstance(Integer sessionId) {
		sessionPath.toFile().mkdirs();
		File file = fileSession(sessionId);

		if (file.exists()) {
			return new SerializeUtil<SessionManager>(file).unserialize();
			
		}
		
		return new SessionManager(sessionId);
	}

	private static File fileSession(Integer id) {
		File file = sessionPath.resolve("user_" + id).toFile();
		return file;
	}
	
	public void save() {
		File file = fileSession(sessionId);
		new SerializeUtil<SessionManager>(file).serialize(this);
	}
	
	public SessionManager put(String key, Object o) {
		storage.put(key, o);
		return this;
	}

	public <T> T get(String key, Class<T> classOfT) {
		Object object = storage.get(key);
		return Primitives.wrap(classOfT).cast(object);
	}
	
	public Object get(String key) {
		return storage.get(key);
	}
	
	public SessionManager remove(String key) {
		storage.remove(key);
		return this;
	}
	
	public boolean containsKey(String key) {
		return storage.containsKey(key);
	}

	@Override
	public String toString() {
		return storage.toString();
	}
	
}
