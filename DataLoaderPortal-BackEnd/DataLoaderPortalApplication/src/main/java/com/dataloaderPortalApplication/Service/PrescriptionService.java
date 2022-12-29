package com.dataloaderPortalApplication.Service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.stereotype.Service;

import com.dataloaderPortalApplication.model.Prescription;
import com.dataloaderPortalApplication.repository.PrescriptionRepository;

@Service
@Configurable
public class PrescriptionService {
	private static final Logger log = LoggerFactory.getLogger(PrescriptionService.class);
	@Autowired
	private PrescriptionRepository prescriptionRepository;
	
		public Prescription save(Prescription p) {
		log.info("START");
		log.debug("Prescription {}",p);
		Prescription result=prescriptionRepository.save(p);
		log.info("END");
		return result;
	}

}
