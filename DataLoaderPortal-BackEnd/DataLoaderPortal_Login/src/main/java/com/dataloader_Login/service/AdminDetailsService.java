package com.dataloader_Login.service;

import java.util.ArrayList;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.dataloader_Login.exception.UserNotFoundException;
import com.dataloader_Login.model.AdminUser;
import com.dataloader_Login.repository.AdminUserRepository;

@Service
public class AdminDetailsService implements UserDetailsService {
	
	private static final Logger log = LoggerFactory.getLogger(AdminDetailsService.class);

	@Autowired
	private AdminUserRepository adminUserRepository;
	
	@Autowired
	private JwtUtil jwtUtil;
	
	private String adminUserDesc="Admin User {}";

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		log.debug("Username {}",username);
		AdminUser adminUser = adminUserRepository.findById(username).orElseThrow(() -> new UserNotFoundException(
				"YOU ARE NOT AN AUTHENTICATED USER. PLEASE TRY TO LOGIN WITH THE VALID CREDENTIALS."));
		log.debug("USER {}",adminUser);
		return new User(adminUser.getUsername(), adminUser.getPassword(), new ArrayList<>());
	}
	
	public String forgotPassword(String username) {
		log.info("START");
		log.debug("Username {}",username);
		Optional<AdminUser> result = adminUserRepository.findById(username);
		if(result.isEmpty()) {
			return "Invalid Username";
		}
		AdminUser adminUser=result.get();
		log.debug(adminUserDesc,adminUser);
		final UserDetails userDetails=new User(adminUser.getUsername(),adminUser.getPassword(),new ArrayList<>());
		log.debug("User Details {}",userDetails);
		adminUser.setResetPasswordToken(jwtUtil.generateToken(userDetails));
		adminUser=adminUserRepository.save(adminUser);
		log.debug(adminUserDesc,adminUser);
		log.info("END");
		return adminUser.getResetPasswordToken();
	}
	
	public String resetPassword(String token,String password) {
		log.info("START");
		log.debug("Token {}",token);
		log.debug("Password {}",password);
		Optional<AdminUser> userOptional=Optional.ofNullable(adminUserRepository.findByResetPasswordToken(token));
		log.debug("User Optional {}",userOptional);
		if(!userOptional.isPresent()) {
			return "False Invalid Token";
		}
		AdminUser adminUser=userOptional.get();
		adminUser.setPassword(password);
		adminUser.setResetPasswordToken(null);
		adminUserRepository.save(adminUser);
		log.debug(adminUserDesc,adminUser);
		log.info("END");
		return "Your Password successfully updated";
	}

}
