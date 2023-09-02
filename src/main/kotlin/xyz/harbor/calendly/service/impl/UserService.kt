package xyz.harbor.calendly.service.impl

import kotlinx.coroutines.reactor.awaitSingle
import kotlinx.coroutines.reactor.awaitSingleOrNull
import org.springframework.stereotype.Service
import xyz.harbor.calendly.core.db.UserRepository
import xyz.harbor.calendly.model.User
import xyz.harbor.calendly.service.IUserService

/**
 * UserService is a service class that provides functionalities for managing users.
 *
 * It contains methods for creating a new user and fetching an existing user based on their ID.
 *
 */
@Service
class UserService(
    val userRepository: UserRepository
) : IUserService {

    /**
     * Creates a new user record in the database after checking for uniqueness by email.
     *
     * @param user The User object to be saved. Should contain a unique email address.
     * @return The saved User object if successful, null otherwise.
     */
    override suspend fun createUser(user: User): User? {
        // check if user already exists with this email id
        val existingUser = userRepository.findByEmail(user.email).awaitSingleOrNull()
        if (existingUser != null) {
            return null
        }
        return userRepository.save(user).awaitSingle()
    }

    /**
     * Fetches a user by their user ID.
     *
     * @param userId The user ID of the user to be fetched.
     * @return User object if found, null otherwise.
     */
    override suspend fun getUser(userId: String): User? {
       return userRepository.findByUserId(userId).awaitSingleOrNull()
    }
}