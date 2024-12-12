package com.yoesoff.movieratingsystem.controller

import com.yoesoff.movieratingsystem.dto.UserDTO
import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.service.UserService
import jakarta.validation.Valid
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*
import org.springframework.data.domain.Page

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

    private val passwordEncoder = BCryptPasswordEncoder()

    @PostMapping
    fun createUser(@Valid @RequestBody user: User): ResponseEntity<UserDTO> {
        val savedUser = userService.saveUser(user)
        return ResponseEntity.ok(UserDTO(savedUser.id, savedUser.username, savedUser.roles))
    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<UserDTO> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(UserDTO(user.id, user.username, user.roles, user.reviews, user.ratings))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUsers(
        @RequestParam(defaultValue = "0") page: Int,
        @RequestParam(defaultValue = "10") size: Int
    ): ResponseEntity<Page<UserDTO>> {
        val usersPage = userService.getAllUsers(page, size)
        val userDTOs = usersPage.map { UserDTO(it.id, it.username, it.roles, it.reviews, it.ratings) }
        return ResponseEntity.ok(userDTOs)
    }

    @PutMapping("/{id}")
    fun updateUser(@PathVariable id: Long, @Valid @RequestBody user: User): ResponseEntity<UserDTO> {
        val updatedUser = userService.updateUser(id, user)
        return if (updatedUser != null) {
            ResponseEntity.ok(UserDTO(updatedUser.id, updatedUser.username, updatedUser.roles))
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return if (userService.deleteUser(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }
}
