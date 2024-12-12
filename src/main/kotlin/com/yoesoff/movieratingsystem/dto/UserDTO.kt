package com.yoesoff.movieratingsystem.dto

import com.yoesoff.movieratingsystem.entity.Rating
import com.yoesoff.movieratingsystem.entity.Review
import com.yoesoff.movieratingsystem.enum.Role

data class UserDTO(
    val id: Long? = null,
    val username: String,
    val roles: List<Role>,
    val reviews: List<Review> = mutableListOf(),
    val ratings: List<Rating> = mutableListOf()

)
