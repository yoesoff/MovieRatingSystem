package com.yoesoff.movieratingsystem.service

import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userRepository: UserRepository
) {

    fun getUserById(id: Long): User? {
        return userRepository.findById(id).orElse(null)
    }

    fun getAllUsers(): List<User> {
        return userRepository.findAll()
    }

//    fun updateUser(id: Long, user: User): User? {
//        return if (userRepository.existsById(id)) {
//            val updatedUser = user.copy(id = id, password = passwordEncoder.encode(user.password))
//            userRepository.save(updatedUser)
//        } else {
//            null
//        }
//    }

    fun deleteUser(id: Long): Boolean {
        return if (userRepository.existsById(id)) {
            userRepository.deleteById(id)
            true
        } else {
            false
        }
    }

//    fun saveUser(user: User): User {
//        user.password = passwordEncoder.encode(user.password)
//        return userRepository.save(user)
//    }

    fun getUserFromUserDetails(userDetails: UserDetails): User? {
        return userRepository.findByUsername(userDetails.username)
    }
}