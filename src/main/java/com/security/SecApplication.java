package com.security;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SecApplication {

	public static void main(String[] args) {
		SpringApplication.run(SecApplication.class, args);
	}

	// @Autowired 
	// private PasswordEncoder passwordEncoder;

	// @Bean
	// public CommandLineRunner createPasswordCommand(){
	// 	return args ->{
	// 		System.out.println(passwordEncoder.encode("123"));
	// 		System.out.println(passwordEncoder.encode("456"));
	// 	};
	// }
}
