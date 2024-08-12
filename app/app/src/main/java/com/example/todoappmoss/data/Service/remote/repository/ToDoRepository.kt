package com.example.todoappmoss.data.Service.remote.repository

import com.example.todoappmoss.bloc.NetworkClient
import com.example.todoappmoss.data.model.ToDoItem
import com.google.gson.Gson

class ToDoRepository {
    private val baseUrl = "https://your-backend-url.com/api/todoitem"
    private val networkClient = NetworkClient()

    fun getToDoItems(): List<ToDoItem>? {
        val response = networkClient.get(baseUrl)
        return if (response != null) {
            Gson().fromJson(response, Array<ToDoItem>::class.java).toList()
        } else {
            null
        }
    }

}