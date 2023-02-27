package com.finescore.moneycookie;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class MoneycookieApplication{
	public static void main(String[] args) {
		SpringApplication.run(MoneycookieApplication.class, args);
	}

}
