package com.service.stajnet;

import java.time.LocalDateTime;
import java.util.HashSet;

import com.service.stajnet.model.Address;
import com.service.stajnet.model.ComputerSkill;
import com.service.stajnet.model.Contact;
import com.service.stajnet.model.EducationHistory;
import com.service.stajnet.model.ForeignLanguage;
import com.service.stajnet.model.ForeignLanguage.Language;
import com.service.stajnet.model.ForeignLanguage.Level;
import com.service.stajnet.model.JobHistory;
import com.service.stajnet.model.Project;
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
				Address address = Address.builder().address("9131/16 Sok. No: 4 Daire: 10 Şoförler Sitesi Refet Bele Mahallesi").city("İzmir").country("Turkey").district("Karabağlar").postCode("35160").build();
				Contact contact = Contact.builder().address(address).fax("-").phone("+90 5336859858").website("-").build();
				HashSet<Role> roles = new HashSet<Role>();
				roles.add(Role.builder().role("admin").build());
				roles.add(Role.builder().role("user").build());
				HashSet<Social> socials = new HashSet<Social>();
				socials.add(Social.builder().address("https://www.linkedin.com/in/furkankayar/").type(Social.Type.LINKEDIN).build());
				socials.add(Social.builder().address("https://www.github.com/furkankayar/").type(Social.Type.GITHUB).build());
				socials.add(Social.builder().address("https://www.instagram.com/6675726b616e6b61796172/").type(Social.Type.INSTAGRAM).build());
				HashSet<ForeignLanguage> foreignLanguages = new HashSet<ForeignLanguage>();
				foreignLanguages.add(ForeignLanguage.builder()
										.languageName(Language.ENGLISH)
										.listeningLevel(Level.B1)
										.readingLevel(Level.B2)
										.writingLevel(Level.B2)
										.speakingLevel(Level.B1)
										.build());
				foreignLanguages.add(ForeignLanguage.builder()
										.languageName(Language.GERMAN)
										.listeningLevel(Level.B1)
										.readingLevel(Level.B2)
										.writingLevel(Level.B2)
										.speakingLevel(Level.NATIVE)
										.build());
				LocalDateTime birthdate = LocalDateTime.of(1998, 10, 13, 0, 0, 0);

				HashSet<JobHistory> jobHistories = new HashSet<JobHistory>();
				LocalDateTime jobStart = LocalDateTime.of(2020, 7, 20, 0, 0, 0);
				LocalDateTime jobEnd = LocalDateTime.of(2020, 9, 7, 0, 0, 0);
				jobHistories.add(JobHistory.builder() 
									.companyName("ASELSAN")
									.position("Intern")
									.explanation("ASELSAN MGEO Aviyonik Yazılım Tasarım Müdürlüğü'nde 6 haftalık staj dönemi boyunca çalıştım.")
									.isWorking(false)
									.startDate(jobStart)
									.endDate(jobEnd)
									.build());
				LocalDateTime jobStart2 = LocalDateTime.of(2020, 9, 20, 0, 0, 0);
				jobHistories.add(JobHistory.builder() 
									.companyName("Test Company")
									.position("Software Engineer")
									.explanation("Text Explanation")
									.isWorking(true)
									.startDate(jobStart2)
									.build());

				HashSet<EducationHistory> educationHistories = new HashSet<EducationHistory>();
				LocalDateTime eduStart = LocalDateTime.of(2018, 8, 3, 0, 0, 0);
				LocalDateTime eduStart2 = LocalDateTime.of(2012, 9, 17, 0, 0, 0);
				LocalDateTime eduEnd2 = LocalDateTime.of(2016, 6, 17, 0, 0, 0);
				educationHistories.add(EducationHistory.builder()
										.type(EducationHistory.Type.UNDERGRADUATE)
										.schoolName("Dokuz Eylül University")
										.facultyName("Engineering Faculty")
										.departmentName("Computer Engineering")
										.startDate(eduStart)
										.educationStatus(EducationHistory.Status.STUDENT)
										.gradingSystem(EducationHistory.GradingSystem.FOUR)
										.gpa(3.92f)
										.classNumber("Grade 4")
										.build());
				educationHistories.add(EducationHistory.builder()
										.type(EducationHistory.Type.HIGH_SCHOOL)
										.schoolName("Övgü Terzibaşıoğlu High School")
										.startDate(eduStart2)
										.endDate(eduEnd2)
										.gradingSystem(EducationHistory.GradingSystem.HUNDRED)
										.gpa(82.48f)
										.educationStatus(EducationHistory.Status.GRADUATED)
										.build());
				
				HashSet<Project> projects = new HashSet<Project>();
				projects.add(Project.builder()
								.name("Record Analyzer")
								.description("The tool that is used to analyze messages of 1553 records.")
								.build());
				projects.add(Project.builder()
								.name("Test Project")
								.description("Test project desctiption.")
								.build());

				HashSet<ComputerSkill> computerSkills = new HashSet<ComputerSkill>();
				computerSkills.add(ComputerSkill.builder()
									.name("Linux")
									.level("User Level")
									.explanation("I have been used Linux based operating systems for over 2 years.")
									.build());
				computerSkills.add(ComputerSkill.builder()
									.name("C Programming Language")
									.level("3/5")
									.explanation("I have developed multithreaded programs on Linux based operating systems. I have also developed Linux system calls.")
									.build());

				userService.save(
					User.builder()
						.username("furkankayar")
						.firstName("Furkan")
						.lastName("Kayar")
						.email("furkankayar27@gmail.com")
						.contact(contact)
						.gender(User.Gender.MALE)
						.nationality(User.Nationality.TR)
						.birthdate(birthdate)
						.password(hashedPassword)
						.socials(socials)
						.roles(roles)
						.foreignLanguages(foreignLanguages)
						.jobHistories(jobHistories)
						.educationHistories(educationHistories)
						.projects(projects)
						.computerSkills(computerSkills)
						.build()
				);
			}
		};
	}

	public static void main(String[] args) {
		SpringApplication.run(StajnetApplication.class, args);
		
	}

}
