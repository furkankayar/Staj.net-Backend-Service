package com.service.stajnet;

import java.util.HashSet;

import com.service.stajnet.model.Role;
import com.service.stajnet.model.User;
import com.service.stajnet.repository.IUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Configuration
public class StajnetApplication {

	@Autowired
	private IUserRepository userRepository;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Utility utility() {
		return new Utility();
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return new CommandLineRunner(){
			@Override
			public void run(String... args) throws Exception {
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = bCryptPasswordEncoder.encode("123");
				HashSet<Role> roles = new HashSet<Role>();
				roles.add(new Role("admin"));
				roles.add(new Role("user"));
				userRepository.save(
					User.builder()
						.username("furkankayar")
						.firstName("furkan")
						.lastName("kayar")
						.encryptedPassword(hashedPassword)
						.roles(roles)
						.build()
				);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(StajnetApplication.class, args);
	}

}
