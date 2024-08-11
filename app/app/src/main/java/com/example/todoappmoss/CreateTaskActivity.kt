package com.example.todoappmoss

import android.app.Activity
import android.app.TimePickerDialog
import android.content.Intent
import android.os.Bundle
import android.view.View
import android.widget.AdapterView
import android.widget.Button
import android.widget.CalendarView
import android.widget.EditText
import android.widget.ImageButton
import android.widget.Spinner
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.annotation.OptIn
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.example.todoappmoss.data.model.ToDoItem
import com.example.todoappmoss.ui.createtask.CreateTaskViewModel
import com.example.todolistapp.R

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

            val resultIntent = Intent()
            resultIntent.putExtra("new_task", newTask)
            setResult(Activity.RESULT_OK, resultIntent)
            finish()
        }


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

            val intent = Intent(this, TaskBoardActivity::class.java)
            startActivity(intent)

            Toast.makeText(this, "Task saved: ${selectedDate.toString()}", Toast.LENGTH_SHORT).show()

            finish()

        }


    }


}
