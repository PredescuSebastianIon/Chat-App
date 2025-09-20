package com.chat.backend.security;

import com.chat.backend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configurers.userdetails.DaoAuthenticationConfigurer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public InMemoryUserDetailsManager inMemoryUsers() {
        UserDetails admin = User
                .withUsername("admin")
                .roles("ADMIN")
                .password(passwordEncoder().encode("capsunele"))
                .build();
        return new InMemoryUserDetailsManager(admin);
    }

    @Bean
    public AuthenticationProvider inMemoryAuth(InMemoryUserDetailsManager inMemoryAuth, PasswordEncoder passwordEncoder) {
        var authMethod = new DaoAuthenticationProvider();
        authMethod.setUserDetailsService(inMemoryAuth);
        authMethod.setPasswordEncoder(passwordEncoder);
        return authMethod;
    }

    @Bean
    public AuthenticationProvider postgresAuth(PasswordEncoder passwordEncoder, AuthService authService) {
        var authMethod = new DaoAuthenticationProvider();
        authMethod.setUserDetailsService(authService);
        authMethod.setPasswordEncoder(passwordEncoder);
        return authMethod;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity httpSecurity,
                                                   AuthenticationProvider inMemoryAuth,
                                                   AuthenticationProvider postgresAuth) throws Exception {
        httpSecurity
                .authenticationProvider(inMemoryAuth)
                .authenticationProvider(postgresAuth);
        return httpSecurity
            .csrf(AbstractHttpConfigurer::disable)
            .formLogin(httpForm -> {
                httpForm.loginPage("/login").permitAll();
                httpForm.defaultSuccessUrl("/home", true);
            })
            .authorizeHttpRequests(registry -> {
                registry.requestMatchers("/login", "/register", "/assets/**", "/css/**", "/javascript/**").permitAll();
                registry.anyRequest().authenticated();
            })
            .logout(logout -> {
                logout.logoutUrl("/logout")
                .logoutSuccessUrl("/login");
            })
            .build();
    }
}
