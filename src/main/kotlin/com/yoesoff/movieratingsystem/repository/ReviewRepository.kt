package com.yoesoff.movieratingsystem.repository

import com.yoesoff.movieratingsystem.entity.Movie
import com.yoesoff.movieratingsystem.entity.Review
import org.springframework.data.jpa.repository.JpaRepository

interface ReviewRepository : JpaRepository<Review, Long> {
    fun findByMovie(movie: Movie): List<Review>
}