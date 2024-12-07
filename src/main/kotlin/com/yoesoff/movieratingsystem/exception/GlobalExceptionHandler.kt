package com.yoesoff.movieratingsystem.exception

import org.springframework.dao.DataIntegrityViolationException
import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.validation.FieldError
import org.springframework.web.bind.MethodArgumentNotValidException
import org.springframework.web.bind.annotation.ControllerAdvice
import org.springframework.web.bind.annotation.ExceptionHandler
import java.time.LocalDateTime

@ControllerAdvice
class GlobalExceptionHandler {

    // Handle validation errors
    @ExceptionHandler(MethodArgumentNotValidException::class)
    fun handleValidationExceptions(ex: MethodArgumentNotValidException): ResponseEntity<Map<String, Any>> {
        val errors = mutableMapOf<String, String>()
        ex.bindingResult.allErrors.forEach { error ->
            val fieldName = (error as FieldError).field
            val errorMessage = error.defaultMessage ?: "Invalid value"
            errors[fieldName] = errorMessage
        }

        val errorResponse = mapOf(
            "status" to HttpStatus.BAD_REQUEST.value(),
            "error" to "Validation Error",
            "message" to "Invalid input data. Please fix the errors and try again.",
            "errors" to errors,
            "timestamp" to LocalDateTime.now()
        )
        return ResponseEntity(errorResponse, HttpStatus.BAD_REQUEST)
    }

    // Handle duplicate key errors (like username already exists)
    @ExceptionHandler(DataIntegrityViolationException::class)
    fun handleDataIntegrityViolationException(ex: DataIntegrityViolationException): ResponseEntity<Map<String, Any>> {
        val errorResponse = mapOf(
            "status" to HttpStatus.CONFLICT.value(),
            "error" to "Conflict",
            "message" to "Username already exists. Please choose a different username.",
            "timestamp" to LocalDateTime.now()
        )
        return ResponseEntity(errorResponse, HttpStatus.CONFLICT)
    }

    @ExceptionHandler(IllegalArgumentException::class)
    fun handleIllegalArgumentException(ex: IllegalArgumentException): ResponseEntity<Map<String, Any>> {
        val errorResponse: Map<String, Any> = mapOf<String, Any>(
            "status" to HttpStatus.BAD_REQUEST.value(),
            "error" to "Bad Request",
            "message" to (ex.message ?: "An error occurred"),
            "timestamp" to LocalDateTime.now().toString() // Convert LocalDateTime to String
        )
        return ResponseEntity<Map<String, Any>>(errorResponse, HttpStatus.BAD_REQUEST)
    }


}
