package re.edu.intern_management_prj.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import lombok.RequiredArgsConstructor;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    public AuthenticationProvider  authenticationProvider() {
        DaoAuthenticationProvider daoAuthenticationProvider = new DaoAuthenticationProvider(userDetailsService);
        daoAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        return daoAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(AuthenticationConfiguration config) {
        return config.getAuthenticationManager(); //
    }
    
    @Bean
    public SecurityFilterChain filterChain(org.springframework.security.config.annotation.web.builders.HttpSecurity http) throws Exception {
        http.csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(auth -> auth
                .requestMatchers(HttpMethod.POST, "/api/auth/login").permitAll()
                .requestMatchers(HttpMethod.GET, "/api/auth/me").hasAnyRole("ADMIN", "MENTOR", "STUDENT")

                .requestMatchers(HttpMethod.GET, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.GET, "/api/users/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/*/status").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/users/*/role").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/students").hasAnyRole("ADMIN", "MENTOR")
                .requestMatchers(HttpMethod.GET, "/api/students/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/students").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/students/*").hasAnyRole("ADMIN", "STUDENT")

                .requestMatchers(HttpMethod.GET, "/api/mentors").hasAnyRole("ADMIN", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/mentors/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/mentors").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/mentors/*").hasAnyRole("ADMIN", "MENTOR")

                .requestMatchers(HttpMethod.GET, "/api/internship_phases").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/internship_phases/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/internship_phases").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/internship_phases/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/internship_phases/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/evaluation_criteria").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/evaluation_criteria/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/evaluation_criteria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/evaluation_criteria/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/evaluation_criteria/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/assessment_rounds").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/assessment_rounds/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/assessment_rounds").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/assessment_rounds/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/assessment_rounds/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/round_criteria").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/round_criteria/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/round_criteria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/round_criteria/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/round_criteria/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/internship_assignments").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/internship_assignments/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/internship_assignments").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PUT, "/api/internship_assignments/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/assessment_results").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/assessment_results").hasRole("MENTOR")
                .requestMatchers(HttpMethod.PUT, "/api/assessment_results/*").hasRole("MENTOR")

                .anyRequest().authenticated()
            )
            .httpBasic(Customizer.withDefaults());

        return http.build();
    }
}
