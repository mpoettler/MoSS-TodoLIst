package com.example.todoappmoss.data.model

import com.google.gson.annotations.SerializedName
import java.io.Serializable


data class Task(
    val id: Int,
    var title: String,
    var description: String? = null,

    var deadline: String,

    @SerializedName("is_completed")
    var isCompleted: Boolean = false,

    @SerializedName("list_id")
    val listId: Int,

    @SerializedName("priority")
    var priority: String? = null,

    @SerializedName("reminder_time")
    var reminderTime: String? = null


) : Serializable

enum class Priority {
    LOW, MEDIUM, HIGH
}
