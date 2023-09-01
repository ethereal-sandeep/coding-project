package xyz.harbor.calendly.service

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import xyz.harbor.calendly.core.db.UserRepository
import xyz.harbor.calendly.model.User

@Service
class UserService(
    val userRepository: UserRepository
) {
    // create user
    suspend fun createUser(user: User): User? {
        // check if user already exists with this email id
        val existingUser = userRepository.findByEmail(user.email).awaitSingleOrNull()
        if (existingUser != null) {
            return null
        }
        return userRepository.save(user).awaitSingle()
    }

    // get user
    suspend fun getUser(userId: String): User? {
       return userRepository.findByUserId(userId).awaitSingleOrNull()
    }
}