package com.yoesoff.movieratingsystem.controller

import com.yoesoff.movieratingsystem.entity.User
import com.yoesoff.movieratingsystem.service.UserService
import org.springframework.http.ResponseEntity
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/users")
class UserController(private val userService: UserService) {

//    @PostMapping
//    fun createUser(@RequestBody user: User): ResponseEntity<User> {
//        val savedUser = userService.saveUser(user)
//        return ResponseEntity.ok(savedUser)
//    }

    @GetMapping("/{id}")
    fun getUserById(@PathVariable id: Long): ResponseEntity<User> {
        val user = userService.getUserById(id)
        return if (user != null) {
            ResponseEntity.ok(user)
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping
    fun getAllUsers(): ResponseEntity<List<User>> {
        val users = userService.getAllUsers()
        return ResponseEntity.ok(users)
    }

//    @PutMapping("/{id}")
//    fun updateUser(@PathVariable id: Long, @RequestBody user: User): ResponseEntity<User> {
//        val updatedUser = userService.updateUser(id, user)
//        return if (updatedUser != null) {
//            ResponseEntity.ok(updatedUser)
//        } else {
//            ResponseEntity.notFound().build()
//        }
//    }

    @DeleteMapping("/{id}")
    fun deleteUser(@PathVariable id: Long): ResponseEntity<Void> {
        return if (userService.deleteUser(id)) {
            ResponseEntity.noContent().build()
        } else {
            ResponseEntity.notFound().build()
        }
    }

    @GetMapping("/door")
    fun getPass(): String {
        val passwordEncoder = BCryptPasswordEncoder()
        val rawPassword = "yusuf123"
        val encodedPassword = passwordEncoder.encode(rawPassword)

        return encodedPassword
    }

}