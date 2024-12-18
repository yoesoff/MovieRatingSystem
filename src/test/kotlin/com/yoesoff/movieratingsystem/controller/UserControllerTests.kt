package com.yoesoff.movieratingsystem.controller

import com.yoesoff.movieratingsystem.dto.UserDTO
import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.service.UserService
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.extension.ExtendWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.Mockito.`when`
import org.mockito.junit.jupiter.MockitoExtension
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.security.core.Authentication
import org.springframework.security.core.context.SecurityContext
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.test.context.ActiveProfiles

/**
 * Unit tests for the UserController class.
 */
@ExtendWith(MockitoExtension::class)
@ActiveProfiles("test")
class UserControllerTests {

    @Mock
    private lateinit var userService: UserService

    @Mock
    private lateinit var userDetailsService: UserDetailsService

    @InjectMocks
    private lateinit var userController: UserController

    /**
     * Test that the getLoggedInUser method returns the user data of the currently logged in user.
     */
    @Test
    fun testGetLoggedInUserReturnsUserData() {
        // Arrange
        val username = "user1"
        val userDetails = org.springframework.security.core.userdetails.User(username, "password", emptyList())
        val user = User(id = 1L, username = username, password = "password", roles = emptyList(), reviews = emptyList(), ratings = emptyList())
        // Mock the security context and authentication
        val authentication = org.mockito.Mockito.mock(Authentication::class.java)
        // Mock the security context and authentication
        val securityContext = org.mockito.Mockito.mock(SecurityContext::class.java)
        SecurityContextHolder.setContext(securityContext)
        `when`(securityContext.authentication).thenReturn(authentication)
        `when`(authentication.principal).thenReturn(userDetails)
        `when`(userService.findByUsername(username)).thenReturn(user)

        // Act to get the response
        val response: ResponseEntity<UserDTO> = userController.getLoggedInUser()

        // Assert that the response status code is 200 and the user data is returned
        assert(response.statusCode == HttpStatus.OK)
        // Assert that the user data is returned
        assert(response.body?.username == username)
    }

    /**
     * Test that the getLoggedInUser method returns a 404 status code when the user is not found.
     */
    @Test
    fun testGetLoggedInUserReturns404WhenUserNotFound() {
        // Arrange
        val username = "user1"
        val userDetails = org.springframework.security.core.userdetails.User(username, "password", emptyList())
        // Mock the security context and authentication
        val authentication = org.mockito.Mockito.mock(Authentication::class.java)
        // Mock the security context and authentication
        val securityContext = org.mockito.Mockito.mock(SecurityContext::class.java)
        SecurityContextHolder.setContext(securityContext)
        `when`(securityContext.authentication).thenReturn(authentication)
        `when`(authentication.principal).thenReturn(userDetails)
        `when`(userService.findByUsername(username)).thenReturn(null)

        // Act to get the response
        val response: ResponseEntity<UserDTO> = userController.getLoggedInUser()

        // Assert that the response status code is 404
        assert(response.statusCode == HttpStatus.NOT_FOUND)
    }
}