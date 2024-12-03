package com.yoesoff.movieratingsystem.repository

import com.yoesoff.movieratingsystem.entity.Movie
import com.yoesoff.movieratingsystem.entity.Rating
import com.yoesoff.movieratingsystem.entity.User
import org.springframework.data.jpa.repository.JpaRepository

interface RatingRepository : JpaRepository<Rating, Long> {
    fun findByUserAndMovie(user: User, movie: Movie): Rating?
}