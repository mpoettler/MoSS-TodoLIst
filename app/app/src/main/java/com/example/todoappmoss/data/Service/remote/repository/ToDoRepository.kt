package com.example.todoappmoss.data.Service.remote.repository

import com.example.todoappmoss.bloc.NetworkClient
import com.example.todoappmoss.data.model.Task
import com.google.gson.Gson

class ToDoRepository {
    private val baseUrl = "https://your-backend-url.com/api/todoitem"
    private val networkClient = NetworkClient()

    fun getToDoItems(): List<Task>? {
        val response = networkClient.get(baseUrl)
        return if (response != null) {
            Gson().fromJson(response, Array<Task>::class.java).toList()
        } else {
            null
        }
    }

}