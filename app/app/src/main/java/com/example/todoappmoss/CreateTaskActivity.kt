package com.example.todoappmoss

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import ApiClient
import android.app.Activity
import android.app.AlertDialog
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.example.todoappmoss.data.model.Task
import com.example.todoappmoss.ui.createtask.CreateTaskViewModel
import com.example.todolistapp.R
import java.io.IOException
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

class CreateTaskActivity : AppCompatActivity() {

    private lateinit var selectedDate: String
    private lateinit var selectedTime: String
    private lateinit var selectedAlarm: String
    private lateinit var selectedRepeat: String

    private lateinit var viewModel: CreateTaskViewModel

    @RequiresApi(Build.VERSION_CODES.O)
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

            val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm", Locale.getDefault())
            val parsedDeadline = LocalDateTime.parse("$selectedDate $selectedTime", formatter)

            val newTask = Task(
                id = System.currentTimeMillis().toInt(),
                title = title,
                description = "Alarm: $selectedAlarm, Repeat: $selectedRepeat",
                deadline = parsedDeadline.toString(),
                isCompleted = false,
                listId = 1,
                createdAt = LocalDateTime.now().toString(),
                repeatInterval = selectedRepeat.takeIf{it.isNotEmpty() },
                reminderTime = TaskUtils.parseReminderTime(selectedAlarm)
            )


            Log.d("CreateTaskActivity", "Save button clicked. Task: $newTask")

            CoroutineScope(Dispatchers.Main).launch {
                try {
                    val success = withContext(Dispatchers.IO) {
                        Log.d("CreateTaskActivity", "Sending task to backend: $newTask")
                        val apiClient = ApiClient()
                        apiClient.postTask(newTask)
                    }

                    if (success) {
                        Log.d("CreateTaskActivity", "Task successfully saved: $newTask")
                        AlertDialog.Builder(this@CreateTaskActivity)
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
                        Log.e("CreateTaskActivity", "Failed to save task: $newTask")
                        Toast.makeText(
                            this@CreateTaskActivity,
                            "Failed to save task",
                            Toast.LENGTH_LONG
                        ).show()
                    }

                } catch (e: IOException) {
                    Log.e("CreateTaskActivity", "Error saving task: ${e.message}", e)
                    Toast.makeText(
                        this@CreateTaskActivity,
                        "Failed to save task: ${e.message}",
                        Toast.LENGTH_LONG
                    ).show()
                }
            }
        }}

    private fun setupNavigationButtons() {
    }
}
