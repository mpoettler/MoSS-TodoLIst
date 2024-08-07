package com.example.todoappmoss.data.model


data class ToDoItem(
    val id: Int,
    val title: String,
    val description: String,
    val deadline: String,
    val isCompleted: Boolean
)
