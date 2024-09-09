package com.todoappmoss.todoappmoss.ui.calendar

import ApiClient
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.todoappmoss.todoappmoss.data.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDate: LiveData<Date> = _selectedDate

    private val apiClient = ApiClient()
    private val _tasks = MutableLiveData<List<Task>>()
    private val _tasksForDate = MutableLiveData<List<Task>>()
    val tasksForDate: LiveData<List<Task>> = _tasksForDate


    fun selectDate(date: Date) {
        _selectedDate.value = date
        loadTasksForDate(date)
    }

    // Load tasks for the selected date
    fun updateTaskCompletion(task: Task, isCompleted: Boolean) {
        viewModelScope.launch(Dispatchers.IO) {
            task.isCompleted = isCompleted
            val success = apiClient.updateTask(task)
            if (success) {
                val updatedList = _tasks.value?.map {
                    if (it.id == task.id) task else it
                }
                _tasks.postValue(updatedList ?: listOf())
            }
        }
    }

    // Load all tasks for the choosen date
    fun loadTasksForDate(date: Date) {
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val allTasks = apiClient.getTodoItems()

                val dateFormatter = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
                val formattedSelectedDate = dateFormatter.format(date)

                Log.d("CalendarViewModel", "Selected Date: $formattedSelectedDate")

                val tasksForDate = allTasks.filter { task ->
                    val taskDate = task.deadline.split("T").firstOrNull()
                    Log.d("CalendarViewModel", "Task Deadline: ${task.deadline}, Task Date: $taskDate")
                    taskDate == formattedSelectedDate
                }
                Log.d("CalendarViewModel", "Tasks for $formattedSelectedDate: ${tasksForDate.size}")
                _tasksForDate.postValue(tasksForDate)
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }
    }}
