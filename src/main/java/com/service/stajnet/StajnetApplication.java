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
				roles.add(Role.builder().role("admin").build());
				roles.add(Role.builder().role("user").build());
				HashSet<Social> socials = new HashSet<Social>();
				socials.add(Social.builder().address("https://www.linkedin.com/in/furkankayar/").type(Social.Type.LINKEDIN).build());
				socials.add(Social.builder().address("https://www.github.com/furkankayar/").type(Social.Type.GITHUB).build());
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
			
				String ecePassword = bCryptPasswordEncoder.encode("123");
				HashSet<Role> eceRoles = new HashSet<Role>();
				eceRoles.add(Role.builder().role("admin").build());
				eceRoles.add(Role.builder().role("user").build());
				HashSet<Social> eceSocials = new HashSet<Social>();
				eceSocials.add(Social.builder().address("https://www.linkedin.com/in/ecekobanc/").type(Social.Type.LINKEDIN).build());
				eceSocials.add(Social.builder().address("https://github.com/ecekobanc").type(Social.Type.GITHUB).build());
				eceSocials.add(Social.builder().address("https://twitter.com/ecekobanc").type(Social.Type.TWITTER).build());
				Calendar eceCal = Calendar.getInstance();
				eceCal.set(Calendar.YEAR, 1998);
				eceCal.set(Calendar.MONTH, Calendar.OCTOBER);
				eceCal.set(Calendar.DAY_OF_MONTH, 2);
				eceCal.set(Calendar.HOUR_OF_DAY, 6);
				eceCal.set(Calendar.MINUTE, 57);
				eceCal.set(Calendar.SECOND, 14);
				userRepository.save(
					User.builder()
						.username("ecekobanc")
						.firstName("ece")
						.lastName("kobanc")
						.gender(User.Gender.FEMALE)
						.nationality("T.C.")
						.birthdate(eceCal.getTime())
						.encryptedPassword(ecePassword)
						.socials(eceSocials)
						.roles(eceRoles)
						.build()
				);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(StajnetApplication.class, args);
		
	}

}
