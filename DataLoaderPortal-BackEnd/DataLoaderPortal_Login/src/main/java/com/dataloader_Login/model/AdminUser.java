package com.dataloader_Login.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.Pattern;

import com.dataloader_Login.annotation.ValidPassword;

import lombok.ToString;


@ToString
@Entity
@Table(name="admin_data")
public class AdminUser {

	@Id
	@Column(name="username")
	@Pattern(regexp = "^(.+)@(\\\\S+)$")
	public String username;
	@Min(value = 8)
	@Max(value = 20)
	@ValidPassword
	@Column(name="password")
	public String password;
	@Column(name="reset_password_token")
	public String resetPasswordToken;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public AdminUser(String username, String password, String resetPasswordToken) {
		super();
		this.username = username;
		this.password = password;
		this.resetPasswordToken = resetPasswordToken;
	}

	public String getResetPasswordToken() {
		return resetPasswordToken;
	}

	public void setResetPasswordToken(String resetPasswordToken) {
		this.resetPasswordToken = resetPasswordToken;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public AdminUser() {
		super();
	}
	
	

	
	

}
