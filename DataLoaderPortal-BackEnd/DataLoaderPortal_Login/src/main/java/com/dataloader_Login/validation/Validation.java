package com.dataloader_Login.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.stereotype.Service;

import com.dataloader_Login.model.AdminUser;

@Service
public class Validation {
	
	private Validation() {
		
	}

	public static boolean validateUserLoginCredential(AdminUser userLoginCredential) {
		System.out.println("Username Blank"+userLoginCredential.getUsername().isBlank());
		System.out.println("Password Blank"+userLoginCredential.getPassword().isBlank());
		System.out.println("Incorrect Username"+userLoginCredential.getUsername().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$"));
		System.out.println("Incorrect Password"+Validation.validPassword(userLoginCredential.getPassword()));
		return !userLoginCredential.getUsername().isBlank() && !userLoginCredential.getPassword().isBlank()&&userLoginCredential.getUsername().matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$")
				&& Validation.validPassword(userLoginCredential.getPassword());

	}

	public static boolean validUsername(String username) {
		
		return username.matches("^[a-zA-Z0-9+_.-]+@[a-zA-Z0-9.-]+$");
	}

	public static boolean validPassword(String password) {

		if (password.length() > 20) {

			return false;
		}
		String regex = "^(?=.*\\d)" + "(?=.*[a-z])(?=.*[A-Z])" + "(?=.*[.@#$%^&+=])" + "(?=\\S+$).{8,20}$";

		Pattern p = Pattern.compile(regex);

		Matcher m = p.matcher(password);

		return m.matches();
	}
}
