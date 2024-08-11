// CreateTaskViewModel.kt
package com.example.todoappmoss.ui.createtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.type.Date

class CreateTaskViewModel : ViewModel() {

    private val _taskName = MutableLiveData<String>()
    val taskName: LiveData<String> = _taskName

    private val _dueDate = MutableLiveData<Date>()
    val dueDate: LiveData<Date> = _dueDate


    /*
    private val _reminder = MutableLiveData<Reminder>()
    val reminder: LiveData<Reminder> = _reminder

    private val _repeat = MutableLiveData<RepeatOption>()
    val repeat: LiveData<RepeatOption> = _repeat

    fun updateTaskName(name: String) {
        _taskName.value = name
    }

    fun updateDueDate(date: Date) {
        _dueDate.value = date
    }

    fun updateReminder(reminder: Reminder) {
        _reminder.value = reminder
    }

    fun updateRepeatOption(option: RepeatOption) {
        _repeat.value = option
    }

     */

    // Logic to save or create the task
    fun saveTask() {
    }
}
