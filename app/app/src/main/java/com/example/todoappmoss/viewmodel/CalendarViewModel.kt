// CalendarViewModel.kt
package com.example.todoappmoss.ui.calendar

import android.app.usage.UsageEvents
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.util.*

class CalendarViewModel : ViewModel() {

    private val _selectedDate = MutableLiveData<Date>()
    val selectedDate: LiveData<Date> = _selectedDate

    private val _events = MutableLiveData<List<UsageEvents.Event>>()
    val events: LiveData<List<UsageEvents.Event>> = _events

    fun selectDate(date: Date) {
        _selectedDate.value = date
        loadEventsForDate(date)
    }

    private fun loadEventsForDate(date: Date) {
        _events.value = listOf()
    }
}
