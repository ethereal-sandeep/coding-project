package xyz.harbor.calendly.service.impl

import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import xyz.harbor.calendly.core.db.UserAvailabilityRepository
import xyz.harbor.calendly.model.UserAvailability
import xyz.harbor.calendly.service.IUserAvailabilityService

@Service
class UserAvailabilityService(
    private val userAvailabilityRepository: UserAvailabilityRepository
) : IUserAvailabilityService {

    override suspend fun createAvailability(userAvailability: UserAvailability): UserAvailability {
        return userAvailabilityRepository.save(userAvailability).awaitSingle()
    }

    override suspend fun getUserAvailabilityByUserId(userId: String) =
        userAvailabilityRepository.getUserAvailabilityByUserId(userId)

    override suspend fun getOverlappingAvailability(userId1: String, userId2: String): List<UserAvailability> {
        val overlappingAvailability = mutableListOf<UserAvailability>()
        userAvailabilityRepository.getOverlappingAvailability(userId1, userId2).collectList().awaitLast().forEach {
            overlappingAvailability.add(convertToUserAvailability(it.userId1, it.availabilityDate1, it.startTime1, it.endTime1))
            overlappingAvailability.add(convertToUserAvailability(it.userId2, it.availabilityDate2, it.startTime2, it.endTime2))
        }
        return overlappingAvailability
    }

    private fun convertToUserAvailability(userId: String, date: Long, startTime: Long, endTime: Long): UserAvailability {
        return UserAvailability(
            userId = userId,
            date = date,
            startTime = startTime,
            endTime = endTime
        )
    }
}
