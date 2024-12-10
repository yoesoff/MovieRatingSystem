package com.yoesoff.movieratingsystem.entity

import com.fasterxml.jackson.annotation.JsonIgnore
import jakarta.persistence.*

@Entity
data class Rating(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne val user: User,
    @ManyToOne @JsonIgnore val movie: Movie,
    var rating: Int
) {
    // No-arg constructor required by Hibernate
    constructor() : this(null, User(), Movie(), 0)
}