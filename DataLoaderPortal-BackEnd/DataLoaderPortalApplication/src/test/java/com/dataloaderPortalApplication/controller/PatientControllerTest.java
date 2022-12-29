package com.dataloaderPortalApplication.controller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.CoreMatchers.hasItem;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.content;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.core.io.ClassPathResource;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMultipartHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.dataloaderPortalApplication.Exception.PatientNotFoundException;
import com.dataloaderPortalApplication.controller.PatientController;
import com.dataloaderPortalApplication.model.Patient;
import com.dataloaderPortalApplication.model.Prescription;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.jayway.jsonpath.JsonPath;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest

public class PatientControllerTest {
	@InjectMocks
	private PatientController controller;

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private Patient patient;

	String token = "Bearer "
			+ "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ4eHh4eHhAZ21haWwuY29tIiwiZXhwIjoxNjYzNzcyOTU4LCJpYXQiOjE2NjM3NzExNTh9.E5gzqzFBRCvSg6kovv6pb6YawxXPH-KypZqIcRWQtVc";

	@Test
	public void testUploadFilePass() throws Exception {
		FileInputStream inputFile = new FileInputStream("C:\\Users\\2116440\\Downloads\\PatientDetails.xlsx");
		MockMultipartFile file = new MockMultipartFile("file", "PatientDetails", "multipart/form-data", inputFile);
		mockMvc.perform(MockMvcRequestBuilders.multipart("/load/patientdata").file("file", file.getBytes())
				.characterEncoding("UTF-8").header("Authorization", token))

				.andExpect(status().isOk());
	}
	
	@Test 
	public void testGetAllPatientPass() throws Exception
    {
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/patients")
				.header("Authorization", token))
				.andExpect(status().isOk())
				.andReturn();
		Integer length = JsonPath.read(result.getResponse().getContentAsString(), "$.length()");
		assertThat(length).isPositive();
        
    }
	
	@Test 
	public void testUpdatePatientPass() throws Exception
    {
		Patient patient = new Patient(3, "Test PatientB", "Building Name, Street, Place, Mumbai, State", "03-23-1959", "sam@23gmail.com", "9087563421", "Inducted", new Prescription("12345-1122-13", "desc"));
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.put("/updatepatient")
				.header("Authorization", token)
				.content(asJsonString(patient))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		
		String content=result.getResponse().getContentAsString();
		Patient resultPatient=new ObjectMapper().readValue(content, Patient.class);
		assertEquals(patient.getId(),resultPatient.getId());
        
    }
	
	@Test 
	public void testProcessPatientPass() throws Exception
    {
		Patient patient = new Patient(3, "Test PatientB", "Building Name, Street, Place, Mumbai, State", "03-23-1959", "sam@23gmail.com", "9087563421", "Inducted", new Prescription("12345-1122-13", "desc"));
		
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.put("/processpatient")
				.header("Authorization", token)
				.content(asJsonString(patient))
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String content=result.getResponse().getContentAsString();
		Patient resultPatient=new ObjectMapper().readValue(content, Patient.class);
		assertThat(resultPatient.getStatus()).isEqualTo("Processed");
        
    }
	
	@Test 
	public void testGetPatientByNamePass() throws Exception
    {
		String name="Test Patient";
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/retrieve/"+name)
				.header("Authorization", token)
				
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
				.andReturn();
		String content=result.getResponse().getContentAsString();
		Patient resultPatient=new ObjectMapper().readValue(content, Patient.class);
		assertNotNull(resultPatient);
        
    }
	
	@Test
	public void testGetPatientByNameFail() throws Exception
    {
		String name="ABC";
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.get("/retrieve/"+name)
				.header("Authorization", token)
				  .contentType(MediaType.APPLICATION_JSON)
				  .accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isBadRequest())
				.andReturn();
		
        
    }

	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}

}
