package com.dataloader_Login.model;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.test.context.junit4.SpringRunner;

import com.dataloader_Login.model.AdminUser;

@RunWith(SpringRunner.class)
public class AdminUserTest {
	
	@Mock
	private AdminUser adminUser;
	
	@Before
	public void setUp() {
		adminUser=new AdminUser("yyyyy@gmail.com", "Gautham1@31", null);
	}

	@Test
	public void testAllArgumentConstructor() {
		AdminUser aUser=new AdminUser("yyyyy@gmail.com", "Gautham1@31", null);
		
		assertEquals("yyyyy@gmail.com", aUser.getUsername());
		assertEquals("Gautham1@31", aUser.getPassword());
		assertEquals(null, aUser.getResetPasswordToken());
	}
}
