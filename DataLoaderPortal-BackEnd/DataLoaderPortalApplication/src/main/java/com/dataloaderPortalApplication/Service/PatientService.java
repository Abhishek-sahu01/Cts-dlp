package com.dataloaderPortalApplication.Service;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.dataloaderPortalApplication.Exception.PatientNotFoundException;
import com.dataloaderPortalApplication.helper.ExcelHelper;
import com.dataloaderPortalApplication.model.Patient;
import com.dataloaderPortalApplication.repository.PatientRepository;

@Service
public class PatientService {

	private static final Logger log = LoggerFactory.getLogger(PatientService.class);
	@Autowired
	private PatientRepository patientRepository;

	@Autowired
	private ExcelHelper excelHelper;
	
	
	private String startDesc="START";

	@Transactional
	public List<String> save(MultipartFile file) throws IOException {
		log.info(startDesc);
		try {

			List<?> result = excelHelper.excelToPatient(file.getInputStream());
			
			List<Patient> patients=(List<Patient>) result.get(0);
			log.debug("Patients {}", patients);
			List<String> messages=(List<String>) result.get(1);
			patientRepository.saveAll(patients);
			log.info("END");
			return messages;
		} catch (IOException e) {
			throw new IOException("fail to store excel data: " + e.getMessage());
		}
		
	}
	
	

	public List<Patient> getAllPatient() {
		log.info(startDesc);
		log.info("END");
		return patientRepository.findAll();
	}

	public Patient getPatientByName(String name) throws PatientNotFoundException {
		log.info(startDesc);
		Patient  patient1 = patientRepository.findByNameAndStatus(name,"INDUCTED");
		Optional<Patient> result = Optional.ofNullable(patient1);
		if (!result.isPresent())
			throw new PatientNotFoundException("Patient is not available");
		log.info("END");
		return result.get();
	}

	@Transactional
	public Patient update(Patient patient) {
		log.info(startDesc);
		log.info("END");
		return patientRepository.save(patient);
	}
	
	@Transactional
	public Patient process(Patient patient) {
		log.info(startDesc);
		patient.setStatus("PROCESSED");
		log.info("END");
		return patientRepository.save(patient);
	}
	
	 public PatientService(PatientRepository repo)
	    {
	        this.patientRepository = repo;
	    }

}
