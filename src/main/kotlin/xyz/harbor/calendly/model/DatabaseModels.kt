package xyz.harbor.calendly.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

abstract class BaseEntity(
    val createdAt: Instant = Instant.now(),
    val updatedAt: Instant = Instant.now(),
    val source: String = ""
)

@Table("user")
data class User(
    @Id
    val id: Long = 0,

    val userId: String,
    val name: String,
    val email: String,
    val timeZone: String // store timezone separately
) : BaseEntity()

@Table("user_availability")
data class UserAvailability(
    @Id
    val id: Long = 0,

    val userId: String,
    val date: Long,
    val startTime: Long, // epoch
    val endTime: Long, // epoch
    val state: AvailabilityState = AvailabilityState.UNAVAILABLE
)