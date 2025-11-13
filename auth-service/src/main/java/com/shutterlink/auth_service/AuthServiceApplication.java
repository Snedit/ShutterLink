package com.shutterlink.auth_service;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class AuthServiceApplication {

	public static void main(String[] args) {
		SpringApplication.run(AuthServiceApplication.class, args);
		
			System.out.print("\007");
			System.out.flush();
			System.out.println("You get this sound when the server restarts");
	}

}
  