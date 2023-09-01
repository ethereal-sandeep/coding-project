package xyz.harbor.calendly.core.db

import org.springframework.data.r2dbc.repository.Query
import org.springframework.data.repository.reactive.ReactiveCrudRepository
import org.springframework.stereotype.Repository
import reactor.core.publisher.Flux
import xyz.harbor.calendly.model.OverlappingUserAvailability
import xyz.harbor.calendly.model.UserAvailability

@Repository
interface UserAvailabilityRepository: ReactiveCrudRepository<UserAvailability, String> {

    @Query("SELECT * FROM user_availability WHERE user_id = :userId")
    fun getUserAvailabilityByUserId(userId: String): Flux<UserAvailability>

    @Query("""
    SELECT 
        t1.user_id AS user_id1, t1.date AS availability_date1, t1.start_time AS start_time1, t1.end_time AS end_time1, 
        t2.user_id AS user_id2, t2.date AS availability_date2, t2.start_time AS start_time2, t2.end_time AS end_time2
    FROM 
        user_availability AS t1
    JOIN 
        user_availability AS t2 ON t1.user_id = :userId1 AND t2.user_id = :userId2
    WHERE
        t1.status = 'AVAILABLE' AND t2.status = 'AVAILABLE'
        AND (
            (t1.start_time <= t2.start_time AND t1.end_time >= t2.end_time) OR
            (t1.start_time >= t2.start_time AND t1.end_time <= t2.end_time) OR
            (t1.start_time BETWEEN t2.start_time AND t2.end_time) OR
            (t1.end_time BETWEEN t2.start_time AND t2.end_time)
        )
    """)
    fun getOverlappingAvailability(userId1: String, userId2: String): Flux<OverlappingUserAvailability>
}