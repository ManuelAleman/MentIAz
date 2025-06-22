package com.mentiaz.mentiaz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import com.mentiaz.mentiaz.auth.security.JwtProperties;

@SpringBootApplication
@EnableConfigurationProperties(JwtProperties.class)
public class MentiazApplication {

	public static void main(String[] args) {
		SpringApplication.run(MentiazApplication.class, args);
	}

	
}