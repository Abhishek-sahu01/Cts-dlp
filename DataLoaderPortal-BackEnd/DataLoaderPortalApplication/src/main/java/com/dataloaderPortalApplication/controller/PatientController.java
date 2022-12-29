package com.dataloaderPortalApplication.controller;

import java.util.List;
import java.util.stream.Collectors;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.dataloaderPortalApplication.Service.PatientService;
import com.dataloaderPortalApplication.entityValidation.Validation;
import com.dataloaderPortalApplication.feign.AuthClient;
import com.dataloaderPortalApplication.helper.ExcelHelper;
import com.dataloaderPortalApplication.model.JwtResponse;
import com.dataloaderPortalApplication.model.Patient;
import com.dataloaderPortalApplication.model.ResponseMessage;


@RestController
@CrossOrigin(allowedHeaders = "*", origins = "http://localhost:4200", allowCredentials = "true")
public class PatientController {

	private static final Logger log = LoggerFactory.getLogger(PatientController.class);
	@Autowired
	private PatientService patientService;

	@Autowired
	private AuthClient authClient;

	private String jwtReponseDesc = "Jwt Response {}";
	private String jwtMsg = "Session Expired. Please login again";
	private String startDesc = "START";


	@PostMapping("/load/patientdata")
	@CrossOrigin(origins = "http://localhost:4200")
	public ResponseEntity<Object> uploadFile(@RequestHeader("authorization") String token,
			@RequestParam("file") MultipartFile file) {
		log.info(startDesc);
		JwtResponse jwtResponse = authClient.verifyToken(token);
		log.debug(jwtReponseDesc, jwtResponse);
		if (!jwtResponse.isValid()) {
			return new ResponseEntity<>(jwtMsg, HttpStatus.BAD_REQUEST);
		}
		String message = "";
		if (ExcelHelper.hasExcelFormat(file)) {
			try {
				List<String> messages = patientService.save(file);
				List<String> error = messages.stream().filter(m -> !m.contains("true")).collect(Collectors.toList());
				if (error.isEmpty()) {
					message = "Uploaded the file successfully: " + file.getOriginalFilename();
					return ResponseEntity.status(HttpStatus.OK).body(new ResponseMessage(message));
				} else {
					return new ResponseEntity<>(error, HttpStatus.OK);
				}

			} catch (Exception e) {
				message = "Could not upload the file: " + file.getOriginalFilename() + e + "!";
				return ResponseEntity.status(HttpStatus.EXPECTATION_FAILED).body(new ResponseMessage(message));
			}
		}

		message = "Please upload an excel file!";

		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(new ResponseMessage(message));
	}

	@GetMapping("/patients")
	public ResponseEntity<Object> getAllPatients(@RequestHeader(name = "authorization", required = true) String token) {
		log.info(startDesc);
		JwtResponse jwtResponse = authClient.verifyToken(token);
		log.debug(jwtReponseDesc, jwtResponse);
		if (!jwtResponse.isValid()) {
			return new ResponseEntity<>(jwtMsg, HttpStatus.BAD_REQUEST);
		}
		try {
			List<Patient> patients = patientService.getAllPatient();
			log.debug("Patients {}", patients);
			log.info("END");
			if (patients.isEmpty()) {
				return new ResponseEntity<>("No patient Available in the  system", HttpStatus.NO_CONTENT);
			}
			return new ResponseEntity<>(patients, HttpStatus.OK);
		} catch (Exception e) {
			return new ResponseEntity<>("Error Occured", HttpStatus.INTERNAL_SERVER_ERROR);
		}

	}	

	@PostMapping(value = "/updatepatient")

	public ResponseEntity<Object> updatePatient(@RequestHeader(name = "authorization", required = true) String token,

			@Valid @RequestBody Patient patient) {
		log.info(startDesc);
		// ResponseEntity<?> responseEntity = null;
		JwtResponse jwtResponse = authClient.verifyToken(token);
		log.debug(jwtReponseDesc, jwtResponse);
		if (!jwtResponse.isValid()) {
			return new ResponseEntity<>(jwtMsg, HttpStatus.BAD_REQUEST);
		}
		log.debug("Before Update {}", patient);
		String validationMessage = Validation.validatePatient(patient);
		if (!validationMessage.contains("true")) {
			return new ResponseEntity<>(validationMessage, HttpStatus.BAD_REQUEST);
		}
		Patient result = patientService.update(patient);
		log.debug("After Update {}", result);
		log.info("END");
		HttpHeaders header = new HttpHeaders();
		//header.add("Access-Control-Allow-Origin", "http://localhost:8081/");
		// responseEntity = new ResponseEntity<>(result, HttpStatus.OK);
		return ResponseEntity.ok().headers(header).body(result);
		// return responseEntity;
	}

	@GetMapping(value = "/retrieve/{name}")
	public ResponseEntity<Object> getPatientByName(@RequestHeader(name = "authorization", required = true) String token,
			@Valid @PathVariable String name) {
		log.info(startDesc);
		JwtResponse jwtResponse = authClient.verifyToken(token);
		log.debug(jwtReponseDesc, jwtResponse);
		if (!jwtResponse.isValid()) {
			return new ResponseEntity<>(jwtMsg, HttpStatus.BAD_REQUEST);
		}
		log.debug("Patient Name {}", name);
		String validationMessage = Validation.validatePatientName(name);
		if (!validationMessage.contains("true")) {
			return new ResponseEntity<>(validationMessage, HttpStatus.BAD_REQUEST);
		}
		Patient result = patientService.getPatientByName(name);
		log.debug("Patient {}", result);
		log.info("END");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

	@PutMapping(value = "/processpatient")
	public ResponseEntity<Object> processPatient(@RequestHeader(name = "authorization", required = true) String token,
			@Valid @RequestBody Patient patient) {
		log.info(startDesc);
		JwtResponse jwtResponse = authClient.verifyToken(token);
		log.debug(jwtReponseDesc, jwtResponse);
		if (!jwtResponse.isValid()) {
			return new ResponseEntity<>(jwtMsg, HttpStatus.BAD_REQUEST);
		}
		log.debug("Before Process {}", patient.getStatus());
		String validationMessage = Validation.validatePatient(patient);
		if (!validationMessage.contains("true")) {
			return new ResponseEntity<>(validationMessage, HttpStatus.BAD_REQUEST);
		}
		Patient result = patientService.process(patient);
		log.debug("After Process {}", patient.getStatus());
		log.info("END");
		return new ResponseEntity<>(result, HttpStatus.OK);
	}

}
