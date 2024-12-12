package com.yoesoff.movieratingsystem.entity

import jakarta.persistence.CascadeType
import jakarta.persistence.Entity
import jakarta.persistence.FetchType
import jakarta.persistence.GeneratedValue
import jakarta.persistence.GenerationType
import jakarta.persistence.Id
import jakarta.persistence.OneToMany
import java.time.LocalDate

@Entity
data class Movie(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    val title: String,
    val description: String,
    val releaseDate: LocalDate,
    val director: String,
    val genre: String,
    @OneToMany(mappedBy = "movie", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val ratings: List<Rating> = mutableListOf(),
    @OneToMany(mappedBy = "movie", cascade = [CascadeType.ALL], fetch = FetchType.LAZY)
    val reviews: List<Review> = mutableListOf()

) {
    // No-arg constructor required by Hibernate
    constructor() : this(null, "", "", LocalDate.now(), "", "")
}