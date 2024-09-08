package com.todoappmoss.todoappmoss.ui.taskboard

import ApiClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MediatorLiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoappmoss.todoappmoss.data.model.Task
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.time.LocalDate
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Locale

class TaskBoardViewModel : ViewModel() {

    val _tasks = MutableLiveData<List<Task>>()
    val tasks: LiveData<List<Task>> = _tasks
    private val apiClient = ApiClient()

    private val _filteredTasks = MediatorLiveData<List<Task>>()
    val filteredTasks: LiveData<List<Task>> get() = _filteredTasks

    init {
        _filteredTasks.addSource(_tasks) { taskList ->
            filterTasksForToday(taskList)
        }
    }

    // Load tasks and sort by priority before posting the result
    fun loadTasks() {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val items = apiClient.getTodoItems()
                // Sort tasks by priority (High -> Medium -> Low)
                val sortedTasks = items.sortedBy { task ->
                    when (task.priority) {
                        "Hoch" -> 1
                        "Mittel" -> 2
                        "Niedrig" -> 3
                        else -> 4
                    }
                }
                _tasks.postValue(sortedTasks)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }

    fun updateTask(task: Task) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                Log.d("TaskBoardViewModel", "Sending task update for task: ${task.id}")
                val success = apiClient.updateTask(task)
                if (success) {
                    Log.d("TaskBoardViewModel", "Task update successful for task: ${task.id}")
                    val updatedList = _tasks.value?.map {
                        if (it.id == task.id) task else it
                    } ?: emptyList()
                    // Sort updated tasks by priority
                    val sortedList = updatedList.sortedBy { updatedTask ->
                        when (updatedTask.priority) {
                            "Hoch" -> 1
                            "Mittel" -> 2
                            "Niedrig" -> 3
                            else -> 4
                        }
                    }
                    _tasks.postValue(sortedList)
                } else {
                    Log.e("TaskBoardViewModel", "Fehler beim Aktualisieren der Aufgabe: ${task.id}")
                }
            } catch (e: IOException) {
                e.printStackTrace()
                Log.e("TaskBoardViewModel", "Fehler beim Senden der Aktualisierung: ${e.message}")
            }
        }
    }

    // Filter tasks for today and sort them by priority
    fun filterTasksForToday(taskList: List<Task>?) {
        val today = LocalDate.now()
        val formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        val filteredList = taskList?.filter { task ->
            task.deadline?.let { deadlineString ->
                val deadlineDateTime = LocalDateTime.parse(deadlineString, formatter)
                deadlineDateTime.toLocalDate() == today
            } ?: false
        }?.sortedBy { task ->
            when (task.priority) {
                "Hoch" -> 1
                "Mittel" -> 2
                "Niedrig" -> 3
                else -> 4
            }
        } ?: emptyList()

        _filteredTasks.value = filteredList
    }


    fun loadSingleTaskById(id: Int) {
        viewModelScope.launch(Dispatchers.IO) {
            try {
                val item = apiClient.getTodoItemById(id)
                item?.let {
                    val currentList = _tasks.value.orEmpty().toMutableList()
                    currentList.add(it)
                    _tasks.postValue(currentList)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }
}
