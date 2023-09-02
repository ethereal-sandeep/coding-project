package xyz.harbor.calendly.service

import reactor.core.publisher.Flux
import xyz.harbor.calendly.model.UserAvailability

interface IUserAvailabilityService {
    suspend fun createAvailability(userAvailability: UserAvailability): UserAvailability

    suspend fun getUserAvailabilityByUserId(userId: String): Flux<UserAvailability>

    suspend fun getOverlappingAvailability(userId1: String, userId2: String): List<UserAvailability>
}