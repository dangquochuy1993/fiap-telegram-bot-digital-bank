package br.com.fiap.telegram.util;

import java.io.Serializable;

import com.google.gson.Gson;

public class RouterAction implements Serializable {
	private String action;	

	public RouterAction(String action) {
		this.action = action;		
	}
	
	public String getAction() {
		return action;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static RouterAction fromJson(String json) {
		return new Gson().fromJson(json, RouterAction.class);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}
