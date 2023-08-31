package xyz.harbor.calendly
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.mockk
import io.mockk.unmockkAll
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.AfterEach
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertNull
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import reactor.core.publisher.Mono
import xyz.harbor.calendly.core.db.UserRepository
import xyz.harbor.calendly.model.User
import xyz.harbor.calendly.service.UserService

class UserServiceTest {

    private val userRepository = mockk<UserRepository>()
    private val userService = UserService(userRepository)

    @BeforeEach
    fun setup() {
    }

    @AfterEach
    fun tearDown() {
        unmockkAll()
    }

    @Test
    fun `should create user successfully`() = runBlocking {
        val newUser = User(1, userId = "1", email = "john@test.com", name = "John", timeZone = "UTC")
        coEvery { userRepository.findByEmail("john@test.com") } returns Mono.empty()
        coEvery { userRepository.save(newUser) } returns Mono.just(newUser)

        val result = userService.createUser(newUser)

        assertEquals(newUser, result)
        coVerify { userRepository.save(newUser) }
    }

    @Test
    fun `should not create user if email already exists`() = runBlocking {
        val existingUser = User(1, userId = "1", email = "john@test.com", name = "John", timeZone = "UTC")
        coEvery { userRepository.findByEmail("john@test.com") } returns Mono.just(existingUser)

        val result = userService.createUser(existingUser)

        assertNull(result)
        coVerify(exactly = 0) { userRepository.save(any()) }
    }

    @Test
    fun `should get user by userId`() = runBlocking {
        val existingUser = User(1, userId = "1", email = "john@test.com", name = "John", timeZone = "UTC")
        coEvery { userRepository.findByUserId("1") } returns Mono.just(existingUser)

        val result = userService.getUser("1")

        assertEquals(existingUser, result)
    }
}
