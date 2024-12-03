package com.yoesoff.movieratingsystem.repository

import com.yoesoff.movieratingsystem.entity.Movie
import org.springframework.data.jpa.repository.JpaRepository

interface MovieRepository : JpaRepository<Movie, Long> {
    fun findByTitleContainingIgnoreCase(title: String): List<Movie>
    fun findByDirectorContainingIgnoreCase(director: String): List<Movie>
    fun findByGenre(genre: String): List<Movie>

}