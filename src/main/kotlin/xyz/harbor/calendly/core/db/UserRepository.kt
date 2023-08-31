package xyz.harbor.calendly.core.db

import org.springframework.data.repository.reactive.ReactiveCrudRepository
import xyz.harbor.calendly.model.User

interface UserRepository : ReactiveCrudRepository<User, String> {
}