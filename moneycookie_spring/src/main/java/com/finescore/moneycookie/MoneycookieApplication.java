package com.finescore.moneycookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = SecurityAutoConfiguration.class)
public class MoneycookieApplication {

	public static void main(String[] args) {
		SpringApplication.run(MoneycookieApplication.class, args);
	}

}
