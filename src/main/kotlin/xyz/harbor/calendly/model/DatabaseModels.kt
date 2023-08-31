package xyz.harbor.calendly.model

import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

abstract class BaseEntity(
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    val source: String = ""
)

@Table("user")
data class User(
    val userId: String,
    val name: String,
    val email: String,
) : BaseEntity()

@Table("user_availability")
data class UserAvailability(
    val userId: String,
    val date: Long,
    val startTime: Long, // epoch
    val endTime: Long, // epoch
    val state: AvailabilityState = AvailabilityState.UNAVAILABLE
)