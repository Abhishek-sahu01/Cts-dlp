package com.dataloader_Login.model;

public class UserToken {
	
	private String username;
	private String authToken;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getAuthToken() {
		return authToken;
	}
	public void setAuthToken(String authToken) {
		this.authToken = authToken;
	}
	public UserToken(String username, String authToken) {
		super();
		this.username = username;
		this.authToken = authToken;
	}
	@Override
	public String toString() {
		return "UserToken [username=" + username + "]";
	}
	public UserToken() {
		super();
	
	}
	
	
	
	

}
