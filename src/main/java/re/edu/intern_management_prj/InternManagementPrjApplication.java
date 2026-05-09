package re.edu.intern_management_prj;

import java.util.TimeZone;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.password.PasswordEncoder;

import re.edu.intern_management_prj.model.entity.User;
import re.edu.intern_management_prj.repository.UserRepository;
import re.edu.intern_management_prj.util.enums.RoleEnum;

@SpringBootApplication
public class InternManagementPrjApplication {

	public static void main(String[] args) {
		TimeZone.setDefault(TimeZone.getTimeZone("Asia/Ho_Chi_Minh"));
		SpringApplication.run(InternManagementPrjApplication.class, args);
	}

    @Bean
    CommandLineRunner initAdmin(UserRepository userRepository,
                                PasswordEncoder passwordEncoder) {
        return args -> {
            if (!userRepository.existsByUsername("admin")) {

                User admin = new User();
				admin.setFullName("Admin");
                admin.setUsername("admin");
                admin.setPasswordHash(passwordEncoder.encode("admin123"));
                admin.setEmail("admin@gmail.com");
                admin.setActive(true);
                admin.setRole(RoleEnum.ADMIN);

                userRepository.save(admin);

                System.out.println("✅ Admin account created: admin / admin123");
            }
        };
    }}
