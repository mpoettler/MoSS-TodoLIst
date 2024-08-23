package com.example.todoappmoss

import android.content.Intent
import android.os.Bundle
import android.widget.CalendarView
import android.widget.ImageView
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmoss.adapter.ToDoItemAdapter
import com.example.todoappmoss.data.model.Task
import com.example.todoappmoss.ui.calendar.CalendarViewModel
import com.example.todolistapp.R
import java.util.*

class CalendarActivity : AppCompatActivity() {

    private lateinit var viewModel: CalendarViewModel
    private lateinit var adapter: ToDoItemAdapter
    private lateinit var taskRecyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_calendar)

        setupNavigationButtons()

        viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

        // Initialize RecyclerView
        val recyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
        adapter = ToDoItemAdapter(emptyList(), ::onTaskClicked)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        // Observe the selected date
        viewModel.selectedDate.observe(this, Observer { date ->
            updateCalendarView(date)
        })

        // Observe the tasks for the selected date
        viewModel.tasksForDate.observe(this, Observer { tasks ->
            updateTasksRecyclerView(tasks)
        })

        // CalendarView interaction
        val calendarView: CalendarView = findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            val selectedDate = Calendar.getInstance()
            selectedDate.set(year, month, dayOfMonth)
            viewModel.selectDate(selectedDate.time)
        }
    }

    private fun onTaskClicked(task: Task) {
        val intent = Intent(this, EditTaskActivity::class.java).apply {
            putExtra("task", task)  // Passing the task to EditTaskActivity
        }
        startActivity(intent)
    }

    private fun setupNavigationButtons() {
        val homeButton: ImageView = findViewById(R.id.navigation_home)
        val addButton: ImageView = findViewById(R.id.navigation_add)
        val calendarButton: ImageView = findViewById(R.id.navigation_calendar)

        homeButton.setOnClickListener {
            val intent = Intent(this, TaskBoardActivity::class.java)
            startActivity(intent)
        }

        addButton.setOnClickListener {
            val intent = Intent(this, CreateTaskActivity::class.java)
            startActivity(intent)
        }

        calendarButton.setOnClickListener {
            val intent = Intent(this, CalendarActivity::class.java)
            startActivity(intent)
        }
    }

    private fun updateCalendarView(date: Date) {
        // Optionally update UI with the selected date
    }

    private fun updateTasksRecyclerView(tasks: List<Task>) {
        adapter.updateData(tasks)
    }
}
