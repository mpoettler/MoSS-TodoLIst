package com.example.todoappmoss

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.todoappmoss.ui.createtask.CreateTaskViewModel
import com.example.todolistapp.R

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var viewModel: CreateTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)


        setupNavigationButtons()

        viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)

        // Observe changes to task name
        viewModel.taskName.observe(this, Observer { name ->
            // Update UI with task name
        })

        // Observe changes to due date
        viewModel.dueDate.observe(this, Observer { date ->
            // Update UI with due date
        })

        // Set listeners for inputs and buttons
        findViewById<Button>(R.id.saveButton).setOnClickListener {
            viewModel.saveTask()
        }
    }

    @OptIn(UnstableApi::class)
    private fun setupNavigationButtons() {
        val cancelButton: Button = findViewById(R.id.saveButton)
        val saveButton: Button  = findViewById(R.id.cancelButton)

        cancelButton.setOnClickListener {
            // Go to TaskBoardActivity
            val intent = Intent(this, TaskBoardActivity::class.java)
            Log.d("CreateTaskActivity", "Cancel button clicked") // Log-Ausgabe
            startActivity(intent)
            finish()
        }

        saveButton.setOnClickListener {
            // Go to CreateTaskActivity
            val intent = Intent(this, TaskBoardActivity::class.java)
            startActivity(intent)
            finish()

        }


    }


}
