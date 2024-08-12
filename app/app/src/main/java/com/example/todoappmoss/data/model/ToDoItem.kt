package com.example.todoappmoss.data.model

import java.io.Serializable
import java.time.LocalDateTime


data class ToDoItem(
    val id: Int,
    val title: String,
    val description: String? = null,
    val deadline: String,
    val isCompleted: Boolean = false,
    val priority: Priority = Priority.MEDIUM,
    val categoryId: Int? = null,
    val reminderTime: LocalDateTime? = null
) : Serializable

enum class Priority {
    LOW, MEDIUM, HIGH
}
