package com.dataloaderPortalApplication.service;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.dataloaderPortalApplication.Service.PrescriptionService;
import com.dataloaderPortalApplication.model.Prescription;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class PrescriptionServiceTest {
	
	@Autowired
	private PrescriptionService prescriptionService;
	
	@Test
	public void testSavePass() {
		Prescription prescription=new Prescription("12345-1234-14","desc");
		Prescription result= prescriptionService.save(prescription);
		assertEquals(prescription.getPatientDrugId(),result.getPatientDrugId());
	}

}
