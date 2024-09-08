// CreateTaskViewModel.kt
package com.todoappmoss.todoappmoss.ui.createtask

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.type.Date

class CreateTaskViewModel : ViewModel() {

    private val _taskName = MutableLiveData<String>()
    val taskName: LiveData<String> = _taskName

    private val _dueDate = MutableLiveData<Date>()
    val dueDate: LiveData<Date> = _dueDate


    // Logic to save or create the task
    fun saveTask() {
    }
}
