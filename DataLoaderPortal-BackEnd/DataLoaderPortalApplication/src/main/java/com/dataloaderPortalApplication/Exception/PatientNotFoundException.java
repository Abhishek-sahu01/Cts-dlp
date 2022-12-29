package com.dataloaderPortalApplication.Exception;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;




public class PatientNotFoundException extends RuntimeException{
	
	private static final Logger log = LoggerFactory.getLogger(PatientNotFoundException.class);
	public PatientNotFoundException(String message) {
		super(message);
		log.info("START");
		log.debug("Message {}",message);
		log.info("END");
	}

}
