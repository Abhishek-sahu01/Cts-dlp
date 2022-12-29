package com.dataloaderPortalApplication.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.jupiter.api.BeforeEach;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;

import com.dataloaderPortalApplication.Exception.PatientNotFoundException;
import com.dataloaderPortalApplication.Service.PatientService;
import com.dataloaderPortalApplication.model.Patient;
import com.dataloaderPortalApplication.model.Prescription;
import com.dataloaderPortalApplication.repository.PatientRepository;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class PatientServiceTest {
	@Mock
	private PatientRepository patientRepository;
	
	@Autowired
	private PatientService patientService;
	
	@BeforeEach
	void setup() {
		this.patientService=new PatientService(this.patientRepository);
	}
	
	
	
	@Test
	public void testSavePass() throws IOException {
		FileInputStream inputFile = new FileInputStream( "C:\\Users\\2116440\\Downloads\\PatientDetails.xlsx");  
		MockMultipartFile file = new MockMultipartFile("file", "PatientDetails", "multipart/form-data", inputFile);
		List<String> message=patientService.save(file);
		assertNotNull(message);
	}
	
	@Test 
	public void testGetAllPatientPass()
    {
		Patient patient = new Patient(10, "Sam", "Chennai", "03-23-1959", "sam@23gmail.com", "9087563421", "Inducted", new Prescription("12345-1234-15", "desc"));
		List<Patient> patientList=new ArrayList<>();
		patientList.add(patient);
		when(patientRepository.findAll()).thenReturn(patientList);
		List<Patient> patients=patientService.getAllPatient();
		assertThat(patients).isNotEmpty();
        
    }
	
	@Test 
	public void testUpdatePatientPass()
    {
		Patient patient = new Patient(3, "Test PatientB", "Building Name, Street, Place, Mumbai, State", "03-23-1959", "sam@23gmail.com", "9087563421", "Inducted", new Prescription("12345-1122-13", "desc"));
		
		
		Patient updatedPatient=patientService.update(patient);
		assertEquals(patient.getId(),updatedPatient.getId());
        
    }
	
	@Test 
	public void testProcessPatientPass()
    {
		Patient patient = new Patient(3, "Test PatientB", "Building Name, Street, Place, Mumbai, State", "03-23-1959", "sam@23gmail.com", "9087563421", "Inducted", new Prescription("12345-1122-13", "desc"));
		
		
		Patient updatedPatient=patientService.process(patient);
		assertEquals("Processed",updatedPatient.getStatus());
        
    }
	
	@Test 
	public void testGetPatientByNamePass() throws IOException
    {
		
		Patient patient=patientService.getPatientByName("Test Patient");
		assertNotNull(patient);
        
    }
	
	@Test(expected=PatientNotFoundException.class)
	public void testGetPatientByNameFail() throws IOException
    {
		Patient patient=patientService.getPatientByName("XXXXXX");
		assertNotNull(patient);
        
    }
	
	
}
