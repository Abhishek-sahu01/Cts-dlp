package com.dataloaderPortalApplication;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
@EnableFeignClients
public class PpApplication {
	private static final Logger log=LoggerFactory.getLogger(PpApplication.class);
	public static void main(String[] args) {
		log.debug("START");
		SpringApplication.run(PpApplication.class, args);
		log.debug("END");
	}

}
