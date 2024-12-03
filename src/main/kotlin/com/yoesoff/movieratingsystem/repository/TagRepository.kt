package com.yoesoff.movieratingsystem.repository

import com.yoesoff.movieratingsystem.entity.Tag
import org.springframework.data.jpa.repository.JpaRepository

interface TagRepository : JpaRepository<Tag, Long>