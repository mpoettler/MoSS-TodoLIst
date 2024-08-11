package com.example.todoappmoss

import android.app.usage.UsageEvents
import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import com.example.todolistapp.R
import com.example.todoappmoss.ui.calendar.CalendarViewModel
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var viewModel: CalendarViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        setupNavigationButtons()

        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        // Observe the selected date
        viewModel.selectedDate.observe(this, Observer { date ->
            // Update UI with the selected date
            updateCalendarView(date)
        })

        // Observe the events list
        viewModel.events.observe(this, Observer { events ->
            // Update the RecyclerView with the new events list
            updateEventsRecyclerView(events)
        })
    }

    private fun setupNavigationButtons() {
        val homeButton: ImageView = findViewById(R.id.navigation_home)
        val addButton: ImageView  = findViewById(R.id.navigation_add)
        val calendarButton: ImageView  = findViewById(R.id.navigation_calendar)

        homeButton.setOnClickListener {
            // Go to TaskBoardActivity
            val intent = Intent(this, TaskBoardActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            // Go to CreateTaskActivity
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

        calendarButton.setOnClickListener {
            // Go to CalendarActivity
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
    }


    private fun updateCalendarView(date: Date) {
        // Logic to update calendar view with the selected date
    }

    private fun updateEventsRecyclerView(events: List<UsageEvents.Event>) {
        // Logic to update RecyclerView with events
    }
}
