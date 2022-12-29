package com.dataloaderPortalApplication.model;

import lombok.Data;
import lombok.ToString;

@Data
@ToString
public class JwtResponse {
	private String username;
	private boolean isValid;
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public boolean isValid() {
		return isValid;
	}
	public void setValid(boolean isValid) {
		this.isValid = isValid;
	}
	public JwtResponse(String username, boolean isValid) {
		super();
		this.username = username;
		this.isValid = isValid;
	}
	public JwtResponse() {
		super();
	}
	
}
