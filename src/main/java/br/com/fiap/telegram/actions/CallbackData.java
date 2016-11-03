package br.com.fiap.telegram.actions;

import com.google.gson.Gson;

public class CallbackData {
	//usando varivaveis pequenas para deixar a transação do callback pequena
	
	//action
	private String a;
	
	//flow
	private int f;	

	public CallbackData(String action, int flow) {
		this.a = action;
		this.f = flow;		
	}
	
	public String getAction() {
		return a;
	}
	
	public int getFlow() {
		return f;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static CallbackData fromJson(String json) {
		return new Gson().fromJson(json, CallbackData.class);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}
