package com.alex;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
public class EmpleosApplication implements CommandLineRunner {

	@Autowired
	PasswordEncoder encoder;

	public static void main(String[] args) {
		SpringApplication.run(EmpleosApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		System.out.println(encoder.encode("luis123"));
		System.out.println(encoder.encode("mari123"));
		System.out.println(encoder.encode("omar123"));
		System.out.println(encoder.encode("daniel123"));
		System.out.println(encoder.encode("jorge123"));
	}
}
