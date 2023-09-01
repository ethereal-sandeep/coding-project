package xyz.harbor.calendly.dto

data class UserAvailabilityRequest(
    val date: Long,
    val startTime: Long,
    val endTime: Long
)