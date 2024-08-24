package com.example.todoappmoss

import android.app.Activity
import android.app.AlertDialog
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoappmoss.data.model.Task
import com.example.todoappmoss.ui.edittask.EditTaskViewModel
import com.example.todolistapp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class EditTaskActivity : AppCompatActivity() {

    private val viewModel: EditTaskViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_edit_task)

        val task = intent.getSerializableExtra("task") as Task
        viewModel.loadTask(task)

        val titleEditText = findViewById<EditText>(R.id.editTaskTitle)
        val descriptionEditText = findViewById<EditText>(R.id.editTaskDescription)
        val deadlineTextView = findViewById<TextView>(R.id.editTaskDeadline)
        val reminderTimeTextView = findViewById<TextView>(R.id.editTaskReminderTime)
        val repeatIntervalTextView = findViewById<TextView>(R.id.editTaskRepeatInterval)

        titleEditText.setText(task.title)
        descriptionEditText.setText(task.description)
        deadlineTextView.text = task.deadline
        reminderTimeTextView.text = task.reminderTime
        repeatIntervalTextView.text = task.repeatInterval

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            task.title = titleEditText.text.toString()
            task.description = descriptionEditText.text.toString()
            task.deadline = deadlineTextView.text.toString()
            task.reminderTime = reminderTimeTextView.text.toString()
            task.repeatInterval = repeatIntervalTextView.text.toString()

            updateTask(task)
        }

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            deleteTask(task.id)
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish()
        }



    }






    private fun updateTask(task: Task) {
        CoroutineScope(Dispatchers.Main).launch {
            val success = viewModel.updateTask(task)
            if (success) {
                Toast.makeText(this@EditTaskActivity, "Task updated successfully", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(this@EditTaskActivity, "Failed to update task", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun deleteTask(taskId: Int) {
        CoroutineScope(Dispatchers.Main).launch {
            val success = viewModel.deleteTask(taskId)
            if (success) {
                Toast.makeText(this@EditTaskActivity, "Task deleted successfully", Toast.LENGTH_LONG).show()
                setResult(Activity.RESULT_OK)
                finish()
            } else {
                Toast.makeText(this@EditTaskActivity, "Failed to delete task", Toast.LENGTH_LONG).show()
            }
        }
    }
}
