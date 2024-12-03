package com.yoesoff.movieratingsystem.controller

import com.yoesoff.movieratingsystem.entity.Movie
import com.yoesoff.movieratingsystem.entity.Rating
import com.yoesoff.movieratingsystem.entity.Review
import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.service.MovieService
import com.yoesoff.movieratingsystem.service.UserService
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/movies")
class MovieController(private val movieService: MovieService,
                      private val userService: UserService) {

    @GetMapping
    fun getAllMovies(): List<Movie> = movieService.getAllMovies()

    @GetMapping("/{id}")
    fun getMovieById(@PathVariable id: Long): ResponseEntity<Movie> {
        val movie = movieService.getMovieById(id)
        return if (movie != null) {
            ResponseEntity.ok(movie)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/search")
    fun searchMovies(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) director: String?,
        @RequestParam(required = false) genre: String?
    ): List<Movie> = movieService.searchMovies(title, director, genre)


    @PostMapping("/{id}/rate")
    fun rateMovie(
        @PathVariable id: Long,
        @RequestBody ratingRequest: Map<String, Int>,
        authentication: Authentication
    ): ResponseEntity<Rating> {
        val userDetails = authentication.principal as org.springframework.security.core.userdetails.User
        val user = userService.getUserFromUserDetails(userDetails) ?: return ResponseEntity.notFound().build()
        val movie = movieService.getMovieById(id) ?: return ResponseEntity.notFound().build()
        val rating = movieService.rateMovie(user, movie, ratingRequest["rating"] ?: 0)
        return ResponseEntity.ok(rating)
    }

    @PostMapping("/{id}/review")
    fun reviewMovie(
        @PathVariable id: Long,
        @RequestBody reviewRequest: Map<String, String>,
        authentication: Authentication
    ): ResponseEntity<Review> {
        val userDetails = authentication.principal as org.springframework.security.core.userdetails.User
        val user = userService.getUserFromUserDetails(userDetails) ?: return ResponseEntity.notFound().build()
        val movie = movieService.getMovieById(id) ?: return ResponseEntity.notFound().build()
        val review = movieService.reviewMovie(user, movie, reviewRequest["reviewText"] ?: "")
        return ResponseEntity.ok(review)
    }

    @GetMapping("/{id}/reviews")
    fun getReviewsForMovie(@PathVariable id: Long): ResponseEntity<List<Review>> {
        val movie = movieService.getMovieById(id) ?: return ResponseEntity.notFound().build()
        val reviews = movieService.getReviewsForMovie(movie)
        return ResponseEntity.ok(reviews)
    }

    @GetMapping("/top-rated")
    fun getTopRatedMovies(): List<Movie> = movieService.getTopRatedMovies()

    @PostMapping
    fun addMovie(@RequestBody movie: Movie): ResponseEntity<Movie> {
        val savedMovie = movieService.addMovie(movie)
        return ResponseEntity(savedMovie,   HttpStatus.CREATED)
    }
}