package com.yoesoff.movieratingsystem.dto

import com.yoesoff.movieratingsystem.enum.Role

data class UserDTO(
    val id: Long? = null,
    val username: String,
    val roles: List<Role>
)
