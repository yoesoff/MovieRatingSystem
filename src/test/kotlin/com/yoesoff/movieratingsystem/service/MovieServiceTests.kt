import com.yoesoff.movieratingsystem.entity.Movie
import com.yoesoff.movieratingsystem.repository.MovieRepository
import com.yoesoff.movieratingsystem.repository.RatingRepository
import com.yoesoff.movieratingsystem.repository.ReviewRepository
import com.yoesoff.movieratingsystem.service.MovieService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.mockito.kotlin.verify
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.test.context.ActiveProfiles
import java.time.LocalDate
import java.util.Optional

@ExtendWith(MockitoExtension::class)
@ActiveProfiles("test")
class MovieServiceTests {

    @Mock
    private lateinit var movieRepository: MovieRepository

    @Mock
    private lateinit var ratingRepository: RatingRepository

    @Mock
    private lateinit var reviewRepository: ReviewRepository

    @InjectMocks
    private lateinit var movieService: MovieService

    @Test
    fun testGetAllMoviesWithPagination() {
        // Arrange: Mock movie data
        val movie1 = Movie(
            id = 1L,
            title = "Inception",
            description = "A mind-bending thriller",
            releaseDate = LocalDate.of(2010, 7, 16),
            director = "Christopher Nolan",
            genre = "Sci-Fi",
            ratings = emptyList()
        )
        val movie2 = Movie(
            id = 2L,
            title = "Interstellar",
            description = "A space exploration epic",
            releaseDate = LocalDate.of(2014, 11, 7),
            director = "Christopher Nolan",
            genre = "Sci-Fi",
            ratings = emptyList()
        )
        val movieList = listOf(movie1, movie2)
        val pageable = PageRequest.of(0, 10)
        val moviePage = PageImpl(movieList, pageable, movieList.size.toLong())

        `when`(movieRepository.findAll(pageable)).thenReturn(moviePage)

        // Act: Call the service method
        val result = movieService.getAllMovies(0, 10)

        // Assert: Verify the result
        assertEquals(2, result.content.size)

        assertEquals("Inception", result.content[0].title)
        assertEquals("Interstellar", result.content[1].title)
    }

    @Test
    fun testGetMovieById() {
        // Arrange: Mock movie data
        val movie = Movie(
            id = 1L,
            title = "Inception",
            description = "A mind-bending thriller",
            releaseDate = LocalDate.of(2010, 7, 16),
            director = "Christopher Nolan",
            genre = "Sci-Fi",
            ratings = emptyList()
        )

        `when`(movieRepository.findById(1L)).thenReturn(Optional.of(movie))

        // Act: Call the service method
        val result = movieService.getMovieById(1L)

        // Assert: Verify the result
        assertEquals(movie, result)
    }

    @Test
    fun testGetMovieByIdNotFound() {
        // Arrange: Mock movie data
        `when`(movieRepository.findById(1L)).thenReturn(Optional.empty())

        // Act: Call the service method
        val result = movieService.getMovieById(1L)

        // Assert: Verify the result
        assertEquals(null, result)
    }

    @Test
    fun testSaveMovie() {
        // Arrange: Mock movie data
        val movie = Movie(
            id = 1L,
            title = "Inception",
            description = "A mind-bending thriller",
            releaseDate = LocalDate.of(2010, 7, 16),
            director = "Christopher Nolan",
            genre = "Sci-Fi",
            ratings = emptyList()
        )

        `when`(movieRepository.save(movie)).thenReturn(movie)

        // Act: Call the service method
        val result = movieService.addMovie(movie)

        // Assert: Verify the result
        assertEquals(movie, result)
    }

    @Test
    fun testDeleteMovie() {
        // Arrange: Mock movie data
        val movie = Movie(
            id = 1L,
            title = "Inception",
            description = "A mind-bending thriller",
            releaseDate = LocalDate.of(2010, 7, 16),
            director = "Christopher Nolan",
            genre = "Sci-Fi",
            ratings = emptyList()
        )

        // Act: Call the service method
        movieService.deleteMovie(movie)

        // Assert: Verify the result
        verify(movieRepository).delete(movie)
    }

    @Test
    fun testDeleteMovieById() {
        // Arrange: Mock movie data
        val movie = Movie(
            id = 1L,
            title = "Inception",
            description = "A mind-bending thriller",
            releaseDate = LocalDate.of(2010, 7, 16),
            director = "Christopher Nolan",
            genre = "Sci-Fi",
            ratings = emptyList()
        )

        // Act: Call the service method
        movieService.deleteMovieById(1L)

        // Assert: Verify the result
        verify(movieRepository).deleteById(1L)
    }

}
