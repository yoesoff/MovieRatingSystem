package com.yoesoff.movieratingsystem.controller

import com.yoesoff.movieratingsystem.entity.Movie
import com.yoesoff.movieratingsystem.entity.Rating
import com.yoesoff.movieratingsystem.entity.Review
import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.service.MovieService
import com.yoesoff.movieratingsystem.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/movies")
class MovieController(private val movieService: MovieService,
                      private val userService: UserService) {

    @Operation(summary = "Get all movies", description = "Retrieve a list of all movies")
    @ApiResponse(responseCode = "200", description = "List of movies returned successfully")
    @GetMapping
    fun getAllMovies(): List<Movie> = movieService.getAllMovies()

    @Operation(summary = "Get a movie by ID", description = "Retrieve a movie by its ID")
    @ApiResponse(responseCode = "200", description = "Movie returned successfully")
    @GetMapping("/{id}")
    fun getMovieById(@PathVariable id: Long): ResponseEntity<Movie> {
        val movie = movieService.getMovieById(id)
        return if (movie != null) {
            ResponseEntity.ok(movie)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Search movies", description = "Search for movies by title, director, or genre")
    @ApiResponse(responseCode = "200", description = "Movies returned successfully")
    @GetMapping("/search")
    fun searchMovies(
        @RequestParam(required = false) title: String?,
        @RequestParam(required = false) director: String?,
        @RequestParam(required = false) genre: String?
    ): List<Movie> = movieService.searchMovies(title, director, genre)


    @Operation(summary = "Rate a movie", description = "Rate a movie on a scale of 1 to 5")
    @ApiResponse(responseCode = "200", description = "Movie successfully rated")
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

    @Operation(summary = "Review a movie", description = "Write a review for a movie")
    @ApiResponse(responseCode = "200", description = "Movie successfully reviewed")
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

    @Operation(summary = "Get reviews for a movie", description = "Retrieve all reviews for a movie")
    @ApiResponse(responseCode = "200", description = "Reviews returned successfully")
    @GetMapping("/{id}/reviews")
    fun getReviewsForMovie(@PathVariable id: Long): ResponseEntity<List<Review>> {
        val movie = movieService.getMovieById(id) ?: return ResponseEntity.notFound().build()
        val reviews = movieService.getReviewsForMovie(movie)
        return ResponseEntity.ok(reviews)
    }

    @Operation(summary = "Get ratings for a movie", description = "Retrieve all ratings for a movie")
    @ApiResponse(responseCode = "200", description = "Ratings returned successfully")
    @GetMapping("/top-rated")
    fun getTopRatedMovies(): List<Movie> = movieService.getTopRatedMovies()

    @Operation(summary = "Add a new movie", description = "Add a new movie to the system")
    @ApiResponse(responseCode = "201", description = "Movie successfully added")
    @PostMapping
    fun addMovie(@RequestBody movie: Movie): ResponseEntity<Movie> {
        val savedMovie = movieService.addMovie(movie)
        return ResponseEntity(savedMovie,   HttpStatus.CREATED)
    }
}