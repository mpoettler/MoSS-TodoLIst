package com.todoappmoss.todoappmoss.data.model

import java.time.LocalDateTime

data class User(
    val userId: Int,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime,
    val PasswordHash: String
)