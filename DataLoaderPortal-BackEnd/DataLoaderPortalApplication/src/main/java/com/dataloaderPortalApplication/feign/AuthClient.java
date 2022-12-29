package com.dataloaderPortalApplication.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestHeader;

import com.dataloaderPortalApplication.model.JwtResponse;

@FeignClient(name="login-service",url="http://localhost:8080")
public interface AuthClient {
	@GetMapping("/api/auth/validate")
	public JwtResponse verifyToken(@RequestHeader(name="authorization",required=true)String token);
}
