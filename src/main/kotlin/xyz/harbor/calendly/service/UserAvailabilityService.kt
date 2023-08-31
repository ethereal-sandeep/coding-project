package xyz.harbor.calendly.service

import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import xyz.harbor.calendly.core.db.UserAvailabilityRepository
import xyz.harbor.calendly.model.UserAvailability

@Service
class UserAvailabilityService(
    private val userAvailabilityRepository: UserAvailabilityRepository
) {

    suspend fun createAvailability(userAvailability: UserAvailability): UserAvailability {
        return userAvailabilityRepository.save(userAvailability).awaitSingle()
    }

    suspend fun getUserAvailabilityByUserId(userId: String) =
        userAvailabilityRepository.getUserAvailabilityByUserId(userId)

    suspend fun getOverlappingAvailability(
        userId1: String,
        userId2: String
    ): List<UserAvailability> {
        val overlappingAvailability = mutableListOf<UserAvailability>()
        userAvailabilityRepository.getOverlappingAvailability(userId1, userId2).collectList().awaitLast().forEach {
            overlappingAvailability.add(
                UserAvailability(
                    userId = it.userId1,
                    date = it.availabilityDate1,
                    startTime = it.startTime1,
                    endTime = it.endTime1
                )
            )
            overlappingAvailability.add(
                UserAvailability(
                    userId = it.userId2,
                    date = it.availabilityDate2,
                    startTime = it.startTime2,
                    endTime = it.endTime2
                )
            )
        }
        return overlappingAvailability
    }
}
