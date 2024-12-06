package com.yoesoff.movieratingsystem.response

data class ApiResponse<T>(
    val status: String,
    val message: String?,
    val data: T?
)
