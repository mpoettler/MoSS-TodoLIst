package com.example.todoappmoss

import android.content.Intent
import android.os.Bundle
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.example.todoappmoss.data.model.Task
import com.example.todoappmoss.adapter.ToDoItemAdapter
import com.example.todoappmoss.ui.taskboard.TaskBoardViewModel
import com.example.todolistapp.R

class TaskBoardActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskBoardViewModel
    private lateinit var adapter: ToDoItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_board)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_todo_list)
        adapter = ToDoItemAdapter(emptyList())
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(TaskBoardViewModel::class.java)

        viewModel.tasks.observe(this, Observer { tasks ->
            updateRecyclerView(tasks)
        })

        viewModel.loadTasks()

        viewModel.loadSingleTaskById(1)

        setupNavigationButtons()
    }

    private fun setupNavigationButtons() {
        val homeButton: ImageButton = findViewById(R.id.navigation_home)
        val addButton: ImageButton = findViewById(R.id.navigation_add)
        val calendarButton: ImageButton = findViewById(R.id.navigation_calendar)

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

    private fun updateRecyclerView(tasks: List<Task>) {
        adapter.updateData(tasks)    }
}
