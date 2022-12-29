package com.dataloader_Login.controller;

import org.slf4j.Logger;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.dataloader_Login.model.AdminUser;
import com.dataloader_Login.model.JwtResponse;
import com.dataloader_Login.model.PasswordCredential;
import com.dataloader_Login.model.UserToken;
import com.dataloader_Login.repository.AdminUserRepository;
import com.dataloader_Login.service.AdminDetailsService;
import com.dataloader_Login.service.JwtUtil;
import com.dataloader_Login.validation.Validation;

@CrossOrigin(allowedHeaders = "*",origins = "*")
@RestController
public class LoginController {
	private static final Logger log = LoggerFactory.getLogger(LoginController.class);

	@Autowired
	private JwtUtil jwtUtil;
	
	@Autowired
	private Validation validation;
	
	@Autowired
	private AdminDetailsService adminDetailsService;
	
	@Autowired
	private AuthenticationManager authenticationManager; 
	
	private String jwtResponseDesc="Jwt Response {}";
	
	private String startDesc="START";
	
	private void authenticate(String username,String password) throws DisabledException,BadCredentialsException,Exception{
		log.info(startDesc);
		log.debug("Username and Password {} {}",username,password);
		try {
			authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
		}
		catch(DisabledException e){
			log.error("Exception: Not a valid user");
			throw new DisabledException("User Disabled",e);
		}
		catch(BadCredentialsException e) {
			log.error("Exception: Not a valid user");
			throw new BadCredentialsException("Invalid Credential",e);
		}
		catch(Exception e) {
			log.error("Exception {}",e);
			throw new Exception(e);
		}
		log.info("END");
	}
	
	
   
	@PostMapping(value="/login")
	public ResponseEntity<?> login(@RequestBody AdminUser userLoginCredential) {
		log.info(startDesc);
		log.debug("User Login Credentials {}",userLoginCredential);
		if(!Validation.validateUserLoginCredential(userLoginCredential)) {
			return new ResponseEntity<>("Enter Valid Credentials",HttpStatus.BAD_REQUEST);
		}
		try {
			authenticate(userLoginCredential.getUsername(),userLoginCredential.getPassword());
		} catch (Exception e) {
			
			return new ResponseEntity<>("YOU ARE NOT AN AUTHENTICATED USER. PLEASE TRY TO LOGIN WITH THE VALID CREDENTIALS.",HttpStatus.BAD_REQUEST);
		}
		final UserDetails userDetails=adminDetailsService.loadUserByUsername(userLoginCredential.getUsername());
		log.debug("User Details {}",userDetails);
		log.info("END");
		return new ResponseEntity<>(new UserToken(userLoginCredential.getUsername(),jwtUtil.generateToken(userDetails)), HttpStatus.OK);
	}
	
	@GetMapping(value="/validate")
	public ResponseEntity<?> getValidity(@RequestHeader("Authorization") final String token){
		log.info(startDesc);
		
		log.debug("Token {}",token);
		String newToken=token.substring(7);
		log.debug("Token After Removing Bearer {}",newToken);
		JwtResponse jwtResponse=new JwtResponse();
		try {
			if(jwtUtil.validateToken(newToken)) {
				jwtResponse.setUsername(jwtUtil.extractUsername(newToken));
				jwtResponse.setValid(true);
				log.debug(jwtResponseDesc,jwtResponse);
				return new ResponseEntity<>(jwtResponse,HttpStatus.OK);

			}
			else {
				log.error("Token validation failed");
				jwtResponse.setValid(false);
				log.debug(jwtResponseDesc,jwtResponse);
				return new ResponseEntity<>(jwtResponse,HttpStatus.MULTI_STATUS);

			}
		} catch (Exception e) {
			
			
			log.debug(jwtResponseDesc,jwtResponse);
			return new ResponseEntity<>("Jwt Token expired",HttpStatus.MULTI_STATUS);
		}
		
		
		
	}
	
	@PostMapping("/forgot_password")
	public ResponseEntity<Object> forgotPassword(@RequestBody String username) {
		log.info(startDesc);
		if(!Validation.validUsername(username)) {
			return new ResponseEntity<>("Enter Valid Username",HttpStatus.BAD_REQUEST);
		}
		log.debug("Username {}",username);
		String response = adminDetailsService.forgotPassword(username);
		log.debug("Response {}",response);
		if(!response.startsWith("Invalid")) {
			response="true "+response;
			
		}
		else {
			response="false "+response;
		}
		log.info("END");
		return new ResponseEntity<>(response,HttpStatus.OK);
	}
	
	@PostMapping("/reset_password")
	public ResponseEntity<Object> resetPassword(@RequestBody PasswordCredential passwordCredential){
		log.info(startDesc);
		
		if(!passwordCredential.getToken().isBlank()&&!Validation.validPassword(passwordCredential.getPassword())) {
			return new ResponseEntity<>("Enter Valid Credentials",HttpStatus.BAD_REQUEST);
		}
		log.info("END");
		return new ResponseEntity<>(adminDetailsService.resetPassword(passwordCredential.getToken(), passwordCredential.getPassword()),HttpStatus.OK);
	}
	
	
	
	
	
	
}
