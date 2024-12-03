package com.yoesoff.movieratingsystem.entity

import jakarta.persistence.*

@Entity
data class Rating(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne val user: User,
    @ManyToOne val movie: Movie,
    var rating: Int
)