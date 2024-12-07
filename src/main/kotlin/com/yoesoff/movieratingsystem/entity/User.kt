package com.yoesoff.movieratingsystem.entity

import com.yoesoff.movieratingsystem.enum.Role
import jakarta.persistence.*
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Size
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import java.time.LocalDateTime

@Entity
@EntityListeners(AuditingEntityListener::class)
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    @field:NotBlank(message = "Username cannot be blank")
    @field:Size(min = 3, max = 50, message = "Username must be between 3 and 50 characters")
    val username: String,

    @Column(nullable = false)
    @field:NotBlank(message = "Password cannot be blank")
    @field:Size(min = 6, message = "Password must be at least 6 characters long")
    var password: String,

    @Column(nullable = false)
    val isEnabled: Boolean = true,

    @ElementCollection(fetch = FetchType.EAGER)
    @Enumerated(EnumType.STRING)
    val roles: List<Role> = listOf(Role.USER),

    @CreatedDate
    @Column(updatable = false)
    val createdDate: LocalDateTime? = null,

    @LastModifiedDate
    var lastModifiedDate: LocalDateTime? = null
) {
    // No-arg constructor required by Hibernate
    constructor() : this(
        id = 0,
        username = "",
        password = "",
        isEnabled = true,
        roles = listOf(Role.USER)
    )
}