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
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import lombok.RequiredArgsConstructor;
import re.edu.intern_management_prj.config.jwt.JwtAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity(securedEnabled = true)
@RequiredArgsConstructor
public class SecurityConfig {
    private final CustomUserDetailsService userDetailsService;
    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;
    private final CustomAuthenticationEntryPoint authenticationEntryPoint;

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
                .requestMatchers(HttpMethod.PATCH, "/api/users/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/users/*/status").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/users/*/role").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/users/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/students").hasAnyRole("ADMIN", "MENTOR")
                .requestMatchers(HttpMethod.GET, "/api/students/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/students").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/students/*").hasAnyRole("ADMIN", "STUDENT")

                .requestMatchers(HttpMethod.GET, "/api/mentors").hasAnyRole("ADMIN", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/mentors/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/mentors").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/mentors/*").hasAnyRole("ADMIN", "MENTOR")

                .requestMatchers(HttpMethod.GET, "/api/internship-phases").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/internship-phases/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/internship-phases").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/internship-phases/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/internship-phases/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/evaluation-criteria").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/evaluation-criteria/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/evaluation-criteria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/evaluation-criteria/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/evaluation-criteria/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/assessment-rounds").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/assessment-rounds/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/assessment-rounds").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/assessment-rounds/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/assessment-rounds/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/round-criteria").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/round-criteria/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/round-criteria").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/round-criteria/*").hasRole("ADMIN")
                .requestMatchers(HttpMethod.DELETE, "/api/round-criteria/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/internship-assignments").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.GET, "/api/internship-assignments/*").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/internship-assignments").hasRole("ADMIN")
                .requestMatchers(HttpMethod.PATCH, "/api/internship-assignments/*").hasRole("ADMIN")

                .requestMatchers(HttpMethod.GET, "/api/assessment-results").hasAnyRole("ADMIN", "MENTOR", "STUDENT")
                .requestMatchers(HttpMethod.POST, "/api/assessment-results").hasRole("MENTOR")
                .requestMatchers(HttpMethod.PATCH, "/api/assessment-results/*").hasRole("MENTOR")

                .anyRequest().authenticated()
            )
            .exceptionHandling(ex -> ex
                .accessDeniedHandler(accessDeniedHandler)
                .authenticationEntryPoint(authenticationEntryPoint)
            )
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
            .httpBasic(Customizer.withDefaults());
        return http.build();
    }
}
