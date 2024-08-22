package com.example.todoappmoss.ui.edittask

import ApiClient
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.todoappmoss.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.io.IOException

class EditTaskViewModel : ViewModel() {

    var task: Task? = null
        private set

    fun loadTask(task: Task) {
        this.task = task
    }

    suspend fun updateTask(updatedTask: Task): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val apiClient = ApiClient()
                //apiClient.updateTask(updatedTask.id, updatedTask)
                true
            } catch (e: IOException) {
                false
            }
        }
    }

    suspend fun deleteTask(taskId: Int): Boolean {
        return withContext(Dispatchers.IO) {
            try {
                val apiClient = ApiClient()
                //apiClient.deleteTask(taskId)
                true
            } catch (e: IOException) {
                false
            }
        }
    }
}
