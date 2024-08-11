package com.example.todoappmoss

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.example.todoappmoss.adapter.ToDoItem
import com.example.todoappmoss.ui.taskboard.TaskBoardViewModel
import com.example.todolistapp.R

class TaskBoardActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskBoardViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_board)

        setupNavigationButtons()

        viewModel = ViewModelProvider(this).get(TaskBoardViewModel::class.java)

        viewModel.tasks.observe(this, Observer { tasks ->
            updateRecyclerView(tasks)
        })

        viewModel.loadTasks()
    }



    private fun setupNavigationButtons() {
        val homeButton: ImageButton = findViewById(R.id.navigation_home)
        val addButton: ImageButton  = findViewById(R.id.navigation_add)
        val calendarButton: ImageButton  = findViewById(R.id.navigation_calendar)

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

    private fun updateRecyclerView(tasks: List<ToDoItem>) {
    }
}
