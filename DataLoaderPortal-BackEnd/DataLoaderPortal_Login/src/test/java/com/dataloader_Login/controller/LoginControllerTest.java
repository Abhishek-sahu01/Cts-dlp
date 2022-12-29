package com.dataloader_Login.controller;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import com.dataloader_Login.controller.LoginController;
import com.dataloader_Login.exception.UserNotFoundException;
import com.dataloader_Login.model.AdminUser;
import com.dataloader_Login.model.JwtResponse;
import com.dataloader_Login.model.PasswordCredential;
import com.dataloader_Login.model.UserToken;
import com.dataloader_Login.service.AdminDetailsService;
import com.dataloader_Login.service.JwtUtil;
import com.fasterxml.jackson.databind.ObjectMapper;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class LoginControllerTest {
	
	@InjectMocks
	private LoginController loginController;
	
	@Autowired
	private MockMvc mockMvc;
	
	@Mock
	private AdminUser adminUser;
	
	@Mock
	private AuthenticationManager authenticationManager;
	
	@Mock
	private JwtUtil jwtutil;
	
	@Mock
	private UsernamePasswordAuthenticationToken authenticationToken;
	
	@Mock
	private AdminDetailsService adminDetailsService;
	
	
	@Before
	public void setup() {
		adminUser.setUsername("xxxxxx@gmail.com");
		adminUser.setPassword("Springboot@123");
	}
	
	@Test
	public void testLoginPass() throws Exception{
		MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.content(asJsonString(new AdminUser("xxxxxx@gmail.com", "Springboot@123", "")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andReturn();
		String content=result.getResponse().getContentAsString();
		UserToken userToken=new ObjectMapper().readValue(content, UserToken.class);
		assertEquals("UserToken [username=xxxxxx@gmail.com]",userToken.toString());
	}
	
	@Test
	public void testValidityPass() throws Exception{
		
		MvcResult resultL=  mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.content(asJsonString(new AdminUser("xxxxxx@gmail.com", "Springboot@123", "")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andReturn();
		String contentL=resultL.getResponse().getContentAsString();
		UserToken userToken=new ObjectMapper().readValue(contentL, UserToken.class);
		String token="Bearer "+userToken.getAuthToken();
		MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.get("/validate").header("Authorization", token)
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andReturn();
		String content=result.getResponse().getContentAsString();
		JwtResponse jwtResponse=new ObjectMapper().readValue(content, JwtResponse.class);
		assertTrue(jwtResponse.isValid());
	}
	
	@Test
	public void testForgotPasswordPass() throws Exception{
		
		MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/forgot_password")
				.content("yyyyyy@gmail.com")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		.andReturn();
		String content=result.getResponse().getContentAsString();
		String isValid=content.substring(0, 4);
		assertEquals("true",isValid);
		
		
	}
	
	@Test
	public void testForgotPasswordFail() throws Exception{
		
		MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/forgot_password")
				.content("abc@gmail.com")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andExpect(status().isOk())
		.andReturn();
		String content=result.getResponse().getContentAsString();
		String isValid=content.substring(0, 5);
		assertEquals("false",isValid);
		
		
	}
	
	@Test
	public void testResetPasswordPass() throws Exception{
		
		MvcResult resultFP=  mockMvc.perform(MockMvcRequestBuilders.post("/forgot_password")
				.content("yyyyyy@gmail.com")
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
				.andReturn();
		String contentFP=resultFP.getResponse().getContentAsString();
		String token=contentFP.substring(5);
		MvcResult result=  mockMvc.perform(MockMvcRequestBuilders.post("/reset_password")
				.content(asJsonString(new PasswordCredential(token,"Gautham12@1")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isOk())
		.andReturn();
		String content=result.getResponse().getContentAsString();
		assertEquals("Your Password successfully updated",content);
		
		
	}
	
	@Test
	public void testLoginFail() throws Exception{
		
		MvcResult result=mockMvc.perform(MockMvcRequestBuilders.post("/login")
				.content(asJsonString(new AdminUser("abc@gmail.com", "Abcd@123", "")))
				.contentType(MediaType.APPLICATION_JSON)
				.accept(MediaType.APPLICATION_JSON))
		.andExpect(status().isBadRequest())
		.andReturn();
		String content=result.getResponse().getContentAsString();
		assertEquals("YOU ARE NOT AN AUTHENTICATED USER. PLEASE TRY TO LOGIN WITH THE VALID CREDENTIALS.",content);
		when(adminDetailsService.loadUserByUsername("abc@gmail.com")).thenThrow(UserNotFoundException.class);
	}
	
	public static String asJsonString(final Object obj) {
		try {
			return new ObjectMapper().writeValueAsString(obj);
		}catch(Exception e) {
			throw new RuntimeException(e);
		}
	}
	

}
