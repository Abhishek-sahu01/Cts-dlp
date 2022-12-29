package com.dataloader_Login;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableWebMvc
@EnableSwagger2
public class DlpLoginApplication {
	private static final Logger log=LoggerFactory.getLogger(DlpLoginApplication.class);

	public static void main(String[] args) {
		log.debug("START");
		SpringApplication.run(DlpLoginApplication.class, args);
		log.debug("END");
	}

}
