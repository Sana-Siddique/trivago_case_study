package com.trivago.casestudy;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@EnableCaching
@SpringBootApplication
public class CasestudyApplication {

	public static void main(String[] args) {
		SpringApplication.run(CasestudyApplication.class, args);
	}

}
