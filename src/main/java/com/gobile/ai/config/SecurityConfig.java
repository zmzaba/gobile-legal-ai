package com.gobile.ai.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
public class SecurityConfig {

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

	    http

	        // =========================================
	        // DISABLE CSRF FOR DEVELOPMENT
	        // =========================================
	        .csrf(csrf -> csrf.disable())

	        // =========================================
	        // AUTHORIZE REQUESTS
	        // =========================================
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers(
	                    "/login",
	                    "/styles.css",
	                    "/css/**",
	                    "/generate"
	            ).permitAll()
	            .anyRequest().authenticated()
	        )

	        // =========================================
	        // LOGIN CONFIG
	        // =========================================
	        .formLogin(form -> form
	            .loginPage("/login")
	            .defaultSuccessUrl("/", true)
	            .permitAll()
	        )

	        // =========================================
	        // LOGOUT CONFIG
	        // =========================================
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login?logout")
	        );

	    return http.build();
	}

    /**
     * DEMO USER (replace later with database / firm directory)
     */
    @Bean
    public InMemoryUserDetailsManager users() {
        UserDetails user = User
                .withUsername("admin@gobilelegailai.com")
                .password("{noop}GobileAI@2026")
                .roles("ATTORNEY")
                .build();

        return new InMemoryUserDetailsManager(user);
    }
}
