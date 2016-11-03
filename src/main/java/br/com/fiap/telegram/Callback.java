package br.com.fiap.telegram;

import com.google.gson.Gson;

public class Callback {
	//usando varivaveis pequenas para deixar a transação do callback pequena
	
	//action
	private String a;
	
	//flow
	private String f;	

	public Callback(String action, String flow) {
		this.a = action;
		this.f = flow;		
	}
	
	public String getAction() {
		return a;
	}
	
	public String getFlow() {
		return f;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static Callback fromJson(String json) {
		return new Gson().fromJson(json, Callback.class);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}
