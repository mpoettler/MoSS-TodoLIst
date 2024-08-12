// TaskBoardViewModel.kt
package com.example.todoappmoss.ui.taskboard

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoappmoss.data.model.ToDoItem

class TaskBoardViewModel : ViewModel() {

    private val _tasks = MutableLiveData<List<ToDoItem>>()
    val tasks: LiveData<List<ToDoItem>> = _tasks

    private val taskList = mutableListOf<ToDoItem>()

    fun loadTasks() {

        if (taskList.isEmpty()) {
            taskList.add(ToDoItem(1, "Test Task 1", "Description 1", "2024-08-12", false))
            taskList.add(ToDoItem(2, "Test Task 2", "Description 2", "2024-08-13", true))
        }

        _tasks.value = taskList
    }

    fun addTask(task: ToDoItem) {
        taskList.add(task)
        _tasks.value = taskList
    }
}
