package xyz.harbor.calendly.service.impl

import kotlinx.coroutines.reactive.awaitLast
import kotlinx.coroutines.reactive.awaitSingle
import org.springframework.stereotype.Service
import xyz.harbor.calendly.core.db.UserAvailabilityRepository
import xyz.harbor.calendly.model.UserAvailability
import xyz.harbor.calendly.service.IUserAvailabilityService

/**
 * UserAvailabilityService is a service class responsible for managing user availability.
 *
 * It contains methods for creating a user's availability, fetching a user's availability,
 * and getting overlapping availability between two users.
 *
 */
@Service
class UserAvailabilityService(
    private val userAvailabilityRepository: UserAvailabilityRepository
) : IUserAvailabilityService {

    /**
     * Saves a new user availability record to the database.
     *
     * @param userAvailability The user availability object to be saved.
     * @return The saved UserAvailability object.
     */
    override suspend fun createAvailability(userAvailability: UserAvailability): UserAvailability {
        return userAvailabilityRepository.save(userAvailability).awaitSingle()
    }

    /**
     * Fetches all availability records of a user by their user ID.
     *
     * @param userId The user ID for whom the availability records are to be fetched.
     * @return List of UserAvailability objects associated with the user ID.
     */
    override suspend fun getUserAvailabilityByUserId(userId: String) =
        userAvailabilityRepository.getUserAvailabilityByUserId(userId)

    /**
     * Fetches overlapping availability records between two users.
     *
     * @param userId1 The ID of the first user.
     * @param userId2 The ID of the second user.
     * @return List of UserAvailability objects that overlap between the two users.
     */
    override suspend fun getOverlappingAvailability(userId1: String, userId2: String): List<UserAvailability> {
        val overlappingAvailability = mutableListOf<UserAvailability>()
        userAvailabilityRepository.getOverlappingAvailability(userId1, userId2).collectList().awaitLast().forEach {
            overlappingAvailability.add(convertToUserAvailability(it.userId1, it.availabilityDate1, it.startTime1, it.endTime1))
            overlappingAvailability.add(convertToUserAvailability(it.userId2, it.availabilityDate2, it.startTime2, it.endTime2))
        }
        return overlappingAvailability
    }

    /**
     * Helper function to create a UserAvailability object.
     *
     * @param userId The user ID.
     * @param date The date of availability.
     * @param startTime The start time of availability.
     * @param endTime The end time of availability.
     * @return The created UserAvailability object.
     */
    private fun convertToUserAvailability(userId: String, date: Long, startTime: Long, endTime: Long): UserAvailability {
        return UserAvailability(
            userId = userId,
            date = date,
            startTime = startTime,
            endTime = endTime
        )
    }
}
