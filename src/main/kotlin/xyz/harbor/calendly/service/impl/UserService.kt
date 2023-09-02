package xyz.harbor.calendly.service.impl

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import xyz.harbor.calendly.core.db.UserRepository
import xyz.harbor.calendly.model.User
import xyz.harbor.calendly.service.IUserService

@Service
class UserService(
    val userRepository: UserRepository
) : IUserService {
    // create user
    override suspend fun createUser(user: User): User? {
        // check if user already exists with this email id
        val existingUser = userRepository.findByEmail(user.email).awaitSingleOrNull()
        if (existingUser != null) {
            return null
        }
        return userRepository.save(user).awaitSingle()
    }

    // get user
    override suspend fun getUser(userId: String): User? {
       return userRepository.findByUserId(userId).awaitSingleOrNull()
    }
}