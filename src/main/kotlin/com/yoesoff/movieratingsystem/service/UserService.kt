package com.yoesoff.movieratingsystem.service


import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.repository.UserRepository
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Service

@Service
class UserService(private val userRepository: UserRepository) {

    fun getUserFromUserDetails(userDetails: UserDetails): User? {
        return userRepository.findByUsername(userDetails.username)
    }
}