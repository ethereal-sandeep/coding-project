package xyz.harbor.calendly.dto

import org.springframework.context.annotation.Bean

data class CreateUserRequest(
    val name: String,
    val email: String,
    val timeZone: String
)