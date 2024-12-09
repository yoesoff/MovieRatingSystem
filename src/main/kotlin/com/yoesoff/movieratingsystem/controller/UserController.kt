package com.yoesoff.movieratingsystem.controller

import com.yoesoff.movieratingsystem.dto.UserDTO
import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.service.UserService
import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.responses.ApiResponse
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    private val passwordEncoder = BCryptPasswordEncoder()

    @Operation(summary = "Create a new user", description = "This endpoint creates a new user in the system")
    @ApiResponse(responseCode = "201", description = "User successfully created")
    @PostMapping
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<UserDTO> {
        val savedUser = userService.saveUser(user)
        return ResponseEntity.ok(UserDTO(savedUser.id, savedUser.username, savedUser.roles))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(UserDTO(user.id, user.username, user.roles))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Get all users", description = "Retrieve a list of all users")
    @ApiResponse(responseCode = "200", description = "List of users returned successfully")
    @GetMapping
    fun getAllUsers(): ResponseEntity<List<UserDTO>> {
        val users = userService.getAllUsers()
        val userDTOs = users.map { UserDTO(it.id, it.username, it.roles) }
        return ResponseEntity.ok(userDTOs)
    }

    @Operation(summary = "Update a user", description = "Update an existing user in the system")
    @ApiResponse(responseCode = "200", description = "User successfully updated")
    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody user: User): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(id, user)
        return if (updatedUser != null) {
            ResponseEntity.ok(UserDTO(updatedUser.id, updatedUser.username, updatedUser.roles))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @Operation(summary = "Delete a user", description = "Delete an existing user from the system")
    @ApiResponse(responseCode = "204", description = "User successfully deleted")
    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return if (userService.deleteUser(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
