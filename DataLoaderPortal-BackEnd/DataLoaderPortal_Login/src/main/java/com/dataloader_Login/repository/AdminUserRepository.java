package com.dataloader_Login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.dataloader_Login.model.AdminUser;

@Repository
public interface AdminUserRepository extends JpaRepository<AdminUser, String> {
	
	
	
	public AdminUser findByResetPasswordToken(String token);
  
}
