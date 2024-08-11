// TaskBoardViewModel.kt
package com.example.todoappmoss.ui.taskboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoappmoss.adapter.ToDoItem
import okhttp3.internal.concurrent.Task

class TaskBoardViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<ToDoItem>>()
    val tasks: LiveData<List<ToDoItem>> = _tasks

    private val taskList = mutableListOf<ToDoItem>()

    fun loadTasks() {
        _tasks.value = taskList
    }

    fun addTask(task: ToDoItem) {
        taskList.add(task)
        _tasks.value = taskList
    }
}
