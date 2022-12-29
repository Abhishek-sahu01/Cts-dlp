package com.dataloader_Login.model;

public class PasswordCredential {
	
	private String token;
	private String password;
	public String getToken() {
		return token;
	}
	public void setToken(String token) {
		this.token = token;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public PasswordCredential(String token, String password) {
		super();
		this.token = token;
		this.password = password;
	}
	public PasswordCredential() {
		super();
	}
	
	

}
