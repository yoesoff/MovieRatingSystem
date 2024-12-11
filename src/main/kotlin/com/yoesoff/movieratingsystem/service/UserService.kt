package com.yoesoff.movieratingsystem.service

import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.repository.UserRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) {

    /**
     * Get a user from the database using the given username.
     *
     * @param username The username of the user.
     * @return The user if found, null otherwise.
     */
    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

/**
     * Get all users from the database.
     *
     * @return A list of all users.
     */
    fun getAllUsers(page: Int, size: Int): Page<User> {
        val pageable = PageRequest.of(page, size)
        return userRepository.findAll(pageable)
    }

    /**
     * Update a user in the database.
     *
     * @param id The ID of the user to update.
     * @param updatedUser The updated user object.
     * @return The updated user.
     */
    fun updateUser(id: Long, updatedUser: User): User {
        // Fetch the existing user
        val existingUser = userRepository.findById(id).orElseThrow {
            IllegalArgumentException("User with ID $id not found")
        }

        // Check if username exists for another user
        if (updatedUser.username != existingUser.username &&
            userRepository.existsByUsername(updatedUser.username)
        ) {
            throw IllegalArgumentException("Username '${updatedUser.username}' is already taken.")
        }

        // Update user properties
        val userToUpdate = existingUser.copy(
            username = updatedUser.username,
            password = passwordEncoder.encode(updatedUser.password), // Encode new password
            isEnabled = updatedUser.isEnabled,
            roles = updatedUser.roles
        )

        return userRepository.save(userToUpdate)
    }

    /**
     * Delete a user from the database.
     *
     * @param id The ID of the user to delete.
     * @return True if the user was deleted, false otherwise.
     */
    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }

    /**
     * Save a new user to the database.
     *
     * @param user The user to save.
     * @return The saved user.
     */
    fun saveUser(user: User): User {
        if (userRepository.existsByUsername(user.username)) {
            throw IllegalArgumentException("Username '${user.username}' is already taken.")
        }

        val encodedPassword = passwordEncoder.encode(user.password)
        user.password = encodedPassword
        return userRepository.save(user)
    }


    /**
     * Get a user from the database using the given UserDetails object.
     *
     * @param userDetails The UserDetails object.
     * @return The user if found, null otherwise.
     */
    fun getUserFromUserDetails(userDetails: UserDetails): User? {
        return userRepository.findByUsername(userDetails.username)
    }
}