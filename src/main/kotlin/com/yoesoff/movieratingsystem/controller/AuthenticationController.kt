package com.yoesoff.movieratingsystem.controller

import com.yoesoff.movieratingsystem.security.JwtUtil
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.security.authentication.AuthenticationManager
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.userdetails.UserDetailsService
import org.springframework.web.bind.annotation.*

@RestController
class AuthenticationController @Autowired constructor(
    private val authenticationManager: AuthenticationManager,
    private val jwtUtil: JwtUtil,
    private val userDetailsService: UserDetailsService
) {

    @PostMapping("/authenticate")
    fun createAuthenticationToken(@RequestBody authenticationRequest: AuthenticationRequest): ResponseEntity<*> {
        authenticationManager.authenticate(
            UsernamePasswordAuthenticationToken(authenticationRequest.username, authenticationRequest.password)
        )
        val userDetails = userDetailsService.loadUserByUsername(authenticationRequest.username)
        val jwt = jwtUtil.generateToken(userDetails.username)
        return ResponseEntity.ok(AuthenticationResponse(jwt))
    }
}

data class AuthenticationRequest(val username: String, val password: String)
data class AuthenticationResponse(val jwt: String)