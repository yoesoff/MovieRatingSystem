package com.yoesoff.movieratingsystem.entity

import jakarta.persistence.*

@Entity
data class User(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long = 0,

    @Column(unique = true, nullable = false)
    val username: String,

    @Column(nullable = false)
    var password: String,

    @Column(nullable = false)
    val isEnabled: Boolean = true,

    @ElementCollection(fetch = FetchType.EAGER)
    val roles: List<String> = listOf()
)