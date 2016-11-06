package br.com.fiap.telegram.util;

import java.io.File;
import java.io.Serializable;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

import com.google.gson.internal.Primitives;

final public class SessionManager implements Serializable {
	private static final long serialVersionUID = -2754015033295421874L;

	private Map<Object, Object> storage = new HashMap<>();
	private static Path sessionPath = Paths.get("data/session");
	private Integer sessionId;
	
	private transient static Map<Integer, SessionManager> instances = new WeakHashMap<>();
	
	private SessionManager(Integer sessionId) {
		this.sessionId = sessionId;
	}

	public static SessionManager getInstance(Integer sessionId) {
		if (!instances.containsKey(sessionId)) {
			sessionPath.toFile().mkdirs();
			File file = fileSession(sessionId);

			if (file.exists()) {
				return new SerializeUtil<SessionManager>(file).unserialize();
				
			}
			
			instances.put(sessionId, new SessionManager(sessionId));	
		}
		
		return instances.get(sessionId);
	}

	private static File fileSession(Integer id) {
		File file = sessionPath.resolve("user_" + id).toFile();
		return file;
	}
	
	private void save() {
		File file = fileSession(sessionId);
		new SerializeUtil<SessionManager>(file).serialize(this);
	}
	
	public SessionManager put(Object key, Object o) {
		storage.put(key, o);
		this.save();
		return this;
	}

	public <T> T get(Object key, Class<T> classOfT) {
		Object object = get(key);
		return Primitives.wrap(classOfT).cast(object);
	}
	
	public Object get(Object key) {
		return storage.get(key);
	}
	
	public SessionManager remove(Object key) {
		storage.remove(key);
		this.save();
		return this;
	}
	
	public boolean containsKey(Object key) {
		return storage.containsKey(key);
	}

	@Override
	public String toString() {
		return storage.toString();
	}
	
}
