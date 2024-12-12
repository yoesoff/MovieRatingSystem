package com.yoesoff.movieratingsystem.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Review(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne @JsonIgnore val user: User,
    @ManyToOne @JsonIgnore val movie: Movie,
    val reviewText: String
) {
    // No-arg constructor required by Hibernate
    constructor() : this(null, User(), Movie(), "")
}