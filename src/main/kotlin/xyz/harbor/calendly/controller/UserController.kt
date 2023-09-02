package xyz.harbor.calendly.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import xyz.harbor.calendly.dto.CreateUserRequest
import xyz.harbor.calendly.model.User
import xyz.harbor.calendly.service.impl.UserService
import xyz.harbor.calendly.util.Utils


@RestController
@RequestMapping("/user")
class UserController(
    val userService: UserService
) {

    @PostMapping("/create")
    suspend fun createUser(
        @RequestBody userRequest: CreateUserRequest): ResponseEntity<Any> {
        val newUser = User(
            userId = Utils.genId(),
            name = userRequest.name,
            email = userRequest.email,
            timeZone = userRequest.timeZone
        )
        val createdUser = userService.createUser(newUser) ?: return ResponseEntity("User already exists!", HttpStatus.BAD_REQUEST)
        return ResponseEntity.ok(createdUser)
    }

    @GetMapping("/{userId}")
    suspend fun getUser(
        @PathVariable userId: String
    ): ResponseEntity<out Any> {
        val user = userService.getUser(userId) ?: return ResponseEntity("User does not exist!", HttpStatus.BAD_REQUEST)
        return  ResponseEntity.ok(user)
    }
}