package com.example.todoappmoss.data.model

import java.time.LocalDateTime

data class User(
    val id: Int,
    val username: String,
    val email: String,
    val createdAt: LocalDateTime,
    val avatarUrl: String? = null
)