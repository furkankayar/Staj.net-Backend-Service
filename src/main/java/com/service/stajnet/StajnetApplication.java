package com.service.stajnet;

import java.util.Calendar;
import java.util.HashSet;

import com.service.stajnet.model.Role;
import com.service.stajnet.model.Social;
import com.service.stajnet.model.User;
import com.service.stajnet.security.JwtTokenAuthenticationFilter;
import com.service.stajnet.service.IUserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootApplication
@Configuration
public class StajnetApplication {

	@Autowired
	private IUserService userService;

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}

	@Bean
	public Utility utility() {
		return new Utility();
	}

	@Bean
	public FilterRegistrationBean<JwtTokenAuthenticationFilter> registration(JwtTokenAuthenticationFilter filter) {
    	FilterRegistrationBean<JwtTokenAuthenticationFilter> registration = new FilterRegistrationBean<JwtTokenAuthenticationFilter>(filter);
    	registration.setEnabled(false);
    	return registration;
	}

	@Bean
	CommandLineRunner commandLineRunner() {
		return new CommandLineRunner(){
			@Override
			public void run(String... args) throws Exception {
	
				BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
				String hashedPassword = bCryptPasswordEncoder.encode("756ee75b");
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
				userService.save(
					User.builder()
						.username("furkankayar")
						.firstName("Furkan")
						.lastName("Kayar")
						.email("furkankayar27@gmail.com")
						.gender(User.Gender.MALE)
						.nationality(User.Nationality.TC)
						.birthdate(cal.getTime())
						.password(hashedPassword)
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
