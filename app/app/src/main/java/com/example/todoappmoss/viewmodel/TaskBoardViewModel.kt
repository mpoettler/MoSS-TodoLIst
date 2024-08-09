// TaskBoardViewModel.kt
package com.example.todoappmoss.ui.taskboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import okhttp3.internal.concurrent.Task

class TaskBoardViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks

    fun loadTasks() {
        _tasks.value = listOf(/* Load tasks here */)
    }

    fun addTask(task: Task) {
        val updatedTasks = _tasks.value.orEmpty().toMutableList()
        updatedTasks.add(task)
        _tasks.value = updatedTasks
    }

}
