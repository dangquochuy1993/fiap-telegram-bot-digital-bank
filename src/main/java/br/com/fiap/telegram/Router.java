package br.com.fiap.telegram;

import java.io.Serializable;

import com.google.gson.Gson;

/**
 * Classe responsável por controlar o roteamento das requisições pelo telegram
 * @author Diego.Saouda
 */
public class Router implements Serializable {
	
	private String controller;	
	private String action;	

	public Router(String controller, String action) {
		this.controller = controller;
		this.action = action;		
	}
	
	public String getController() {
		return controller;
	}
	
	public String getAction() {
		return action;
	}

	public String toJson() {
		return new Gson().toJson(this);
	}
	
	public static Router fromJson(String json) {
		return new Gson().fromJson(json, Router.class);
	}
	
	@Override
	public String toString() {
		return toJson();
	}
}
