package com.yoesoff.movieratingsystem.repository

import com.yoesoff.movieratingsystem.entity.Movie
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.domain.Pageable

interface MovieRepository : JpaRepository<Movie, Long> {
    fun findByTitleContainingIgnoreCase(title: String): List<Movie>
    fun findByDirectorContainingIgnoreCase(director: String): List<Movie>
    fun findByGenre(genre: String): List<Movie>


// @TODO: Implement the following queries
//    @Query("SELECT m FROM Movie m ORDER BY m.releaseDate DESC")
//    fun findMostRecentMovies(): List<Movie>

    @Query("SELECT m FROM Movie m JOIN m.ratings r GROUP BY m ORDER BY AVG(r.rating) DESC")
    fun findTopRatedMovies(pageable: Pageable): List<Movie>
}