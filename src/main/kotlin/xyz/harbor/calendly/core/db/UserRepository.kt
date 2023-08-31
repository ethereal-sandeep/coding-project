package xyz.harbor.calendly.core.db

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import reactor.core.publisher.Mono
import xyz.harbor.calendly.model.User

interface UserRepository : ReactiveCrudRepository<User, String> {
    fun findByEmail(email: String): Mono<User?>

    fun findByUserId(userId: String): Mono<User?>
}