package xyz.harbor.calendly.controller

import kotlinx.coroutines.reactor.awaitSingle
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import xyz.harbor.calendly.dto.UserAvailabilityRequest
import xyz.harbor.calendly.model.UserAvailability
import xyz.harbor.calendly.service.impl.UserAvailabilityService
import xyz.harbor.calendly.service.impl.UserService

/**
 * [UserAvailabilityController] is responsible for handling requests related to [UserAvailability]s.
 * Endpoints to :-
 *  - create a new [UserAvailability]
 *  - view [UserAvailability] for [userId]
 *  - get overlapping [UserAvailability]s for two given [userId]s
 */
@RestController
@RequestMapping("/user-availability")
class UserAvailabilityController(
    private val userService: UserService,
    private val userAvailabilityService: UserAvailabilityService
) {

    /**
     * Creates a new [UserAvailability] for the given [userId].
     */
    @PostMapping("/create/{userId}")
    suspend fun createAvailability(
        @PathVariable userId: String,
        @RequestBody newAvailabilities: List<UserAvailabilityRequest>
    ): ResponseEntity<String> {
        // check for user existence
        userService.getUser(userId) ?: return ResponseEntity.badRequest().body("User does not exist!")

        newAvailabilities.forEach { newAvailability ->
            val userAvailability = UserAvailability(
                userId = userId,
                date = newAvailability.date,
                startTime = newAvailability.startTime,
                endTime = newAvailability.endTime
            )

            userAvailabilityService.createAvailability(userAvailability)
        }

        return ResponseEntity.ok("Availability added successfully!")
    }

    @GetMapping("/view/{userId}")
    suspend fun viewAvailability(@PathVariable userId: String): ResponseEntity<List<UserAvailability>> {
        val availabilities = userAvailabilityService.getUserAvailabilityByUserId(userId).collectList().awaitSingle()
        return ResponseEntity.ok(availabilities)
    }

    @GetMapping("/overlap/{userId1}/{userId2}")
    suspend fun getOverlappingAvailabilities(
        @PathVariable userId1: String,
        @PathVariable userId2: String
    ): List<UserAvailability> {
        // validate users first
        userService.getUser(userId1) ?: throw IllegalArgumentException("User with id $userId1 does not exist!")
        userService.getUser(userId2) ?: throw IllegalArgumentException("User with id $userId2 does not exist!")

        return userAvailabilityService.getOverlappingAvailability(
            userId1 = userId1,
            userId2 = userId2,
        )
    }

}