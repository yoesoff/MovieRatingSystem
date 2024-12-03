package com.yoesoff.movieratingsystem.repository

import com.yoesoff.movieratingsystem.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<User, Long> {
    fun findByUsername(username: String): User?
}