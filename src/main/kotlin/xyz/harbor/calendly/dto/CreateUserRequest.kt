package xyz.harbor.calendly.dto

data class CreateUserRequest(
    val name: String,
    val email: String,
    val timeZone: String
)