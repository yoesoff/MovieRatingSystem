package com.yoesoff.movieratingsystem.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.web.SecurityFilterChain

@Configuration
class SecurityConfig {

    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        http.csrf().disable() // Nonaktifkan CSRF
            .authorizeRequests()
            .anyRequest().authenticated() // Semua permintaan harus diautentikasi
            .and()
            .httpBasic() // Gunakan HTTP Basic Authentication

        return http.build()
    }
}
