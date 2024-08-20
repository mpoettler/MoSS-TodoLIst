package com.example.todoappmoss.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Task(
    val id: Int,
    val title: String,
    val description: String? = null,

    val deadline: String,

    @SerializedName("is_completed")
    val isCompleted: Boolean = false,

    @SerializedName("list_id")
    val listId: Int,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("repeat_interval")
    val repeatInterval: String? = null,

    @SerializedName("reminder_time")
    val reminderTime: String? = null


) : Serializable

enum class Priority {
    LOW, MEDIUM, HIGH
}
