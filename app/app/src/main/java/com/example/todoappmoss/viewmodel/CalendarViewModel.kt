// CalendarViewModel.kt
package com.example.todoappmoss.ui.calendar

import ApiClient
import android.app.usage.UsageEvents
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.todoappmoss.data.model.Task
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.*

class CalendarViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDate: LiveData<Date> = _selectedDate

    private val _tasksForDate = MutableLiveData<List<Task>>()
    val tasksForDate: LiveData<List<Task>> = _tasksForDate

    fun selectDate(date: Date) {
        _selectedDate.value = date
        loadTasksForDate(date)
    }

    private fun loadTasksForDate(date: Date) {
        // Format the date as a string that matches the format in the backend
        val dateFormat = SimpleDateFormat("yyyy-MM-dd", Locale.getDefault())
        val formattedDate = dateFormat.format(date)

        // Load tasks from API
        CoroutineScope(Dispatchers.IO).launch {
            try {
                // Call the API to get tasks for the selected date
                val tasks = ApiClient().getTasksForDate(formattedDate)
                _tasksForDate.postValue(tasks)
            } catch (e: Exception) {
                e.printStackTrace()
                _tasksForDate.postValue(emptyList()) // Handle failure
            }
        }
    }
}
