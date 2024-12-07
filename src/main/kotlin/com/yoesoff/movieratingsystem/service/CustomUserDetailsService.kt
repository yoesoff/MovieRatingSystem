package com.yoesoff.movieratingsystem.service

import com.yoesoff.movieratingsystem.repository.UserRepository
import org.springframework.security.core.userdetails.User
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.stereotype.Service

@Service
class CustomUserDetailsService(
    private val userRepository: UserRepository
) : UserDetailsService {

    override fun loadUserByUsername(username: String): UserDetails {
        val user = userRepository.findByUsername(username)
            ?: throw IllegalArgumentException("User not found with username: $username")

        // Convert the Role enum values to their string representation
        val roleNames = user.roles.map { it.name } // `it.name` converts Role enum to String

        return User.builder()
            .username(user.username)
            .password(user.password) // Password is already encoded
            .roles(*roleNames.toTypedArray()) // Convert the list of role names to an array of Strings
            .build()
    }
}
