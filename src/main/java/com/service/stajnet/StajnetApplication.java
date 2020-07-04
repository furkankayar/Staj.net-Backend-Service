package com.service.stajnet;

import java.util.Calendar;
import java.util.HashSet;

import com.service.stajnet.model.Role;
import com.service.stajnet.model.Social;
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
				HashSet<Social> socials = new HashSet<Social>();
				socials.add(new Social("https://www.linkedin.com/in/furkankayar/", Social.Type.LINKEDIN));
				socials.add(new Social("https://github.com/furkankayar", Social.Type.GITHUB));
				Calendar cal = Calendar.getInstance();
				cal.set(Calendar.YEAR, 1998);
				cal.set(Calendar.MONTH, Calendar.OCTOBER);
				cal.set(Calendar.DAY_OF_MONTH, 13);
				cal.set(Calendar.HOUR_OF_DAY, 0);
				cal.set(Calendar.MINUTE, 0);
				cal.set(Calendar.SECOND, 0);
				userRepository.save(
					User.builder()
						.username("furkankayar")
						.firstName("furkan")
						.lastName("kayar")
						.gender(User.Gender.MALE)
						.nationality("T.C.")
						.birthdate(cal.getTime())
						.encryptedPassword(hashedPassword)
						.socials(socials)
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
