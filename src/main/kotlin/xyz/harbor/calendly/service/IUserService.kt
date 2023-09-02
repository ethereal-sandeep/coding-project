package xyz.harbor.calendly.service

import xyz.harbor.calendly.model.User

interface IUserService {
    // create user
    suspend fun createUser(user: User): User?

    // get user
    suspend fun getUser(userId: String): User?
}