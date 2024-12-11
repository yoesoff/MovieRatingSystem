package com.yoesoff.movieratingsystem.service

import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.enum.Role
import com.yoesoff.movieratingsystem.repository.UserRepository
import com.yoesoff.movieratingsystem.service.UserService
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.data.domain.PageImpl
import org.springframework.data.domain.PageRequest
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.test.context.ActiveProfiles

@ExtendWith(MockitoExtension::class)
@ActiveProfiles("test")
class UserServiceTests {

    @Mock
    private lateinit var userRepository: UserRepository

    @Mock
    private lateinit var passwordEncoder: PasswordEncoder

    @InjectMocks
    private lateinit var userService: UserService

    @Test
    fun testGetAllUsersWithPagination() {
        // Arrange: Mock user data
        val user1 = User(id = 1L, username = "user1", password = "password1", roles = listOf(Role.USER))
        val user2 = User(id = 2L, username = "user2", password = "password2", roles = listOf(Role.USER))
        val userList = listOf(user1, user2)
        val pageable = PageRequest.of(0, 10)
        val userPage = PageImpl(userList, pageable, userList.size.toLong())

        `when`(userRepository.findAll(pageable)).thenReturn(userPage)

        // Act: Call the service method
        val result = userService.getAllUsers(0, 10)

        // Assert: Verify the result
        assertEquals(2, result.content.size)
        assertEquals("user1", result.content[0].username)
        assertEquals("user2", result.content[1].username)
    }
}