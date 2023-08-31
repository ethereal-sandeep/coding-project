package xyz.harbor.calendly.model

import org.springframework.data.annotation.Id
import org.springframework.data.relational.core.mapping.Table
import java.time.Instant

abstract class BaseEntity(
    var createdAt: Instant = Instant.now(),
    var updatedAt: Instant = Instant.now(),
    var source: String = ""
)

@Table("\"user\"")
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
    val status: AvailabilityState = AvailabilityState.AVAILABLE
)

data class OverlappingUserAvailability(
    val userId1: String,
    val availabilityDate1: Long,
    val startTime1: Long,
    val endTime1: Long,
    val userId2: String,
    val availabilityDate2: Long,
    val startTime2: Long,
    val endTime2: Long
)
