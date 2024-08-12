package com.example.todoappmoss

import ApiClient
import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoappmoss.data.model.ToDoItem
import com.example.todoappmoss.ui.createtask.CreateTaskViewModel
import com.example.todolistapp.R
import okhttp3.OkHttpClient
import okhttp3.Request
import java.io.IOException

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var selectedDate: String
    private lateinit var selectedTime: String
    private lateinit var selectedAlarm: String
    private lateinit var selectedRepeat: String

    private lateinit var viewModel: CreateTaskViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_create_task)

        viewModel = ViewModelProvider(this).get(CreateTaskViewModel::class.java)

        setupNavigationButtons()

        val calendarView: CalendarView = findViewById(R.id.calendarView)
        calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
            selectedDate = "$dayOfMonth/${month + 1}/$year"
        }

        val timePickerButton: Button = findViewById(R.id.timePickerButton)
        timePickerButton.setOnClickListener {
            val timePickerDialog = TimePickerDialog(this, { _, hourOfDay, minute ->
                selectedTime = String.format("%02d:%02d", hourOfDay, minute)
                timePickerButton.text = selectedTime
            }, 12, 0, true)
            timePickerDialog.show()
        }

        val alarmSpinner: Spinner = findViewById(R.id.alarmSpinner)
        alarmSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedAlarm = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedAlarm = "No Alarm"
            }
        }

        val repeatSpinner: Spinner = findViewById(R.id.repeatSpinner)
        repeatSpinner.onItemSelectedListener = object : AdapterView.OnItemSelectedListener {
            override fun onItemSelected(parent: AdapterView<*>, view: View?, position: Int, id: Long) {
                selectedRepeat = parent.getItemAtPosition(position).toString()
            }

            override fun onNothingSelected(parent: AdapterView<*>) {
                selectedRepeat = "No Repeat"
            }
        }

        val saveButton: Button = findViewById(R.id.saveButton)


        saveButton.setOnClickListener {
            val title = findViewById<EditText>(R.id.todoEditText).text.toString()

            val newTask = ToDoItem(
                id = System.currentTimeMillis().toInt(),
                title = title,
                description = "Alarm: $selectedAlarm, Repeat: $selectedRepeat",
                deadline = "$selectedDate $selectedTime",
                isCompleted = false
            )

            try {
                // Task zum Backend senden
                val apiClient = ApiClient()
                val success = apiClient.postTask(newTask)

                if (success) {
                    AlertDialog.Builder(this)
                        .setTitle("Task Saved")
                        .setMessage("Title: ${newTask.title}\nDescription: ${newTask.description}\nDeadline: ${newTask.deadline}")
                        .setPositiveButton("OK") { dialog, _ ->
                            dialog.dismiss()

                            val resultIntent = Intent().apply {
                                putExtra("new_task", newTask)
                            }
                            setResult(Activity.RESULT_OK, resultIntent)
                            finish()
                        }
                        .show()
                } else {
                    Toast.makeText(this, "Failed to save task", Toast.LENGTH_LONG).show()
                }

            } catch (e: IOException) {
                Toast.makeText(this, "Failed to save task: ${e.message}", Toast.LENGTH_LONG).show()
            }
        }

        val cancelButton: Button = findViewById(R.id.cancelButton)
        cancelButton.setOnClickListener {
            val intent = Intent(this, TaskBoardActivity::class.java)
            startActivity(intent)
            finish()
        }
    }

    private fun setupNavigationButtons() {
    }
}
