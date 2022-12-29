package com.dataloader_Login.service;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.test.context.junit4.SpringRunner;

import com.dataloader_Login.exception.UserNotFoundException;
import com.dataloader_Login.model.AdminUser;

@AutoConfigureMockMvc
@RunWith(SpringRunner.class)
@SpringBootTest
public class AdminDetailServiceTest {
	
	@Autowired
	private AdminDetailsService adminDetailsService;
	
	@Mock
	private AdminUser adminUser;
	
	private String token;
	
	@Before
	public void setup() {
		adminUser.setUsername("xxxxxx@gmail.com");
		adminUser.setPassword("Springboot@123");
		token=adminDetailsService.forgotPassword("xxxxxx@gmail.com");
	}
	@Test
	public void testLoadUserByUsernamePass() throws UsernameNotFoundException{
		UserDetails userDetails=adminDetailsService.loadUserByUsername("xxxxxx@gmail.com");
		assertEquals("xxxxxx@gmail.com",userDetails.getUsername());
		assertEquals("Springboot@123",userDetails.getPassword());
	}
	
	@Test(expected=UserNotFoundException.class)
	public void testLoadUserByUsernameFail() throws UsernameNotFoundException{
		UserDetails userDetails=adminDetailsService.loadUserByUsername("abcdef@gmail.com");
		assertNotNull(userDetails);
		
	}
	
	@Test
	public void testForgotPasswordPass() throws UsernameNotFoundException{
		String token=adminDetailsService.forgotPassword("yyyyyy@gmail.com");
		assertNotNull(token);
	}
	@Test(expected=UserNotFoundException.class)
	public void testForgotPasswordFail() throws UsernameNotFoundException{
		String token=adminDetailsService.forgotPassword("abcdef@gmail.com");
		assertNotNull(token);
		
	}
	
	@Test
	public void testResetPasswordPass() {
		String msg=adminDetailsService.resetPassword(token,"Springboot@123");
		assertEquals(msg, "Your Password successfully updated");
	}
	@Test
	public void testResetPasswordFail() {
		String msg=adminDetailsService.resetPassword("abcdefghijklmnopqrstuvwxyz","abcdef@gmail.com");
		assertEquals(msg, "False Invalid Token");
		
	}
	

}
