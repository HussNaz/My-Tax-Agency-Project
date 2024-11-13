package com.example.allAuth;


import com.example.allAuth.Repositories.RoleRepository;
import com.example.allAuth.configurationAndSecurity.AuthEntryPointJwt;
import com.example.allAuth.entity.ERole;
import com.example.allAuth.entity.Role;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import java.time.Instant;

@SpringBootApplication(scanBasePackages = {"com.example.allAuth"})
@EnableJpaRepositories
public class AllAuthApplication implements CommandLineRunner {

	@Autowired
	RoleRepository roleRepository;

	private static final Logger logger = LoggerFactory.getLogger(AllAuthApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(AllAuthApplication.class, args);
		System.out.println("Application Has Started");
		logger.info("Current server time: {}", Instant.now());

	}

	@Override
	public void run(String... args) throws Exception {
		try {
			if (roleRepository.findAll().isEmpty()){
				Role role= new Role();
				role.setName(ERole.ROLE_USER.name());
				Role role1= new Role();
				role1.setName(ERole.ROLE_ADMIN.name());
				roleRepository.save(role);
				roleRepository.save(role1);
			}
		}catch (Exception e){
			System.out.println(e.getMessage());
		}
	}
}
