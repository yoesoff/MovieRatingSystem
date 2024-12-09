package com.yoesoff.movieratingsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.authentication.AuthenticationManager
import com.yoesoff.movieratingsystem.service.CustomUserDetailsService

@Configuration
class SecurityConfig(
    private val customUserDetailsService: CustomUserDetailsService
) {

    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http
            .csrf { it.disable() } // Disable CSRF for simplicity (not recommended for production)
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers(
                        "/users",
                        "/swagger-ui/**",
                        "/swagger-ui.html"
                    ).permitAll() // Allow access to Swagger UI

                    .anyRequest().authenticated() // All other requests require authentication
            }
            .httpBasic { } // Enable Basic Auth

        return http.build()
    }

    // Configure authentication with CustomUserDetailsService
    @Bean
    fun authenticationManager(http: HttpSecurity): AuthenticationManager {
        val auth = http.getSharedObject(AuthenticationManagerBuilder::class.java)
        auth.userDetailsService(customUserDetailsService).passwordEncoder(passwordEncoder())
        return auth.build()
    }
}
