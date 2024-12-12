package com.yoesoff.movieratingsystem.service

import com.yoesoff.movieratingsystem.entity.Movie
import com.yoesoff.movieratingsystem.entity.Rating
import com.yoesoff.movieratingsystem.entity.Review
import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.repository.MovieRepository
import com.yoesoff.movieratingsystem.repository.RatingRepository
import com.yoesoff.movieratingsystem.repository.ReviewRepository
import org.springframework.data.domain.Page
import org.springframework.data.domain.PageRequest
import org.springframework.stereotype.Service

@Service
class MovieService(private val movieRepository: MovieRepository, private val ratingRepository: RatingRepository, private val reviewRepository: ReviewRepository) {

    fun getAllMovies(page: Int, size: Int): Page<Movie> {
        val pageable = PageRequest.of(page, size)
        return movieRepository.findAll(pageable)
    }

    fun getMovieById(id: Long): Movie? = movieRepository.findById(id).orElse(null)

    fun searchMovies(title: String?, director: String?, genre: String?): List<Movie> {
        return when {
            !title.isNullOrEmpty() -> movieRepository.findByTitleContainingIgnoreCase(title)
            !director.isNullOrEmpty() -> movieRepository.findByDirectorContainingIgnoreCase(director)
            !genre.isNullOrEmpty() -> movieRepository.findByGenre(genre)
            else -> movieRepository.findAll()
        }
    }

    fun rateMovie(user: User, movie: Movie, ratingValue: Int): Rating {
        val existingRating = ratingRepository.findByUserAndMovie(user, movie)
        return if (existingRating != null) {
            existingRating.rating = ratingValue
            ratingRepository.save(existingRating)
        } else {
            val newRating = Rating(user = user, movie = movie, rating = ratingValue)
            ratingRepository.save(newRating)
        }
    }

    fun reviewMovie(user: User, movie: Movie, reviewText: String): Review {
        val review = Review(user = user, movie = movie, reviewText = reviewText)
        return reviewRepository.save(review)
    }

    fun getReviewsForMovie(movie: Movie): List<Review> = reviewRepository.findByMovie(movie)

    /**
     * Get top rated movies
     */
    fun getTopRatedMovies(): List<Movie> {
        return movieRepository.findTopRatedMovies(PageRequest.of(0, 5)) // Placeholder
    }

    fun addMovie(movie: Movie): Movie {
        return movieRepository.save(movie)
    }

    fun deleteMovie(movie: Movie) {
        movieRepository.delete(movie)
    }

    fun deleteMovieById(id: Long) {
        movieRepository.deleteById(id)
    }
}