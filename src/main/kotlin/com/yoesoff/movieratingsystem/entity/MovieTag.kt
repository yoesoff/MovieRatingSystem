package com.yoesoff.movieratingsystem.entity

import jakarta.persistence.*

@Entity
data class MovieTag(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,
    @ManyToOne val movie: Movie,
    @ManyToOne val tag: Tag
)