package com.dataloader_Login.exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


public class UserNotFoundException extends RuntimeException{

	private static final Logger log = LoggerFactory.getLogger(UserNotFoundException.class);
	public UserNotFoundException(String message) {
		super(message);
		log.info("START");
		log.debug("Message {}",message);
		log.info("END");
	}

	
}
