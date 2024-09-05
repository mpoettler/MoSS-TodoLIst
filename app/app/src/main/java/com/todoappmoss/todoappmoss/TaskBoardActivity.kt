package at.fhjoanneum.todoappmoss

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.widget.ImageButton
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import at.fhjoanneum.todoappmoss.bloc.NotificationReceiver
import com.example.todolistapp.R
import com.todoappmoss.todoappmoss.data.model.Task
import com.todoappmoss.todoappmoss.adapter.ToDoItemAdapter
import com.todoappmoss.todoappmoss.ui.taskboard.TaskBoardViewModel

class TaskBoardActivity : AppCompatActivity() {

    private lateinit var viewModel: TaskBoardViewModel
    private lateinit var adapter: ToDoItemAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_task_board)

        val recyclerView = findViewById<RecyclerView>(R.id.recycler_view_todo_list)
        adapter = ToDoItemAdapter(emptyList(), ::onTaskClicked, ::onCheckboxChecked)
        recyclerView.adapter = adapter
        recyclerView.layoutManager = LinearLayoutManager(this)

        viewModel = ViewModelProvider(this).get(TaskBoardViewModel::class.java)

        viewModel.filteredTasks.observe(this, Observer { tasks ->
            updateRecyclerView(tasks)
        })

        viewModel.loadTasks()

        setupNavigationButtons()
    }

    private fun onTaskClicked(task: Task) {
        val intent = Intent(this, EditTaskActivity::class.java).apply {
            putExtra("task", task)
        }
        startActivity(intent)
    }

    private fun onCheckboxChecked(task: Task, isChecked: Boolean) {
        task.isCompleted = isChecked
        Log.d("TaskBoardActivity", "Checkbox checked: $isChecked for task: ${task.id}")

        viewModel.updateTask(task)
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
        adapter.updateData(tasks)
    }

    private fun scheduleNotification(context: Context, task: Task, triggerTime: Long) {
        val intent = Intent(context, NotificationReceiver::class.java).apply {
            putExtra("task_title", task.title)
            putExtra("task_description", task.description)
        }

        val pendingIntent = PendingIntent.getBroadcast(
            context,
            task.id,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val alarmManager = context.getSystemService(Context.ALARM_SERVICE) as AlarmManager
        alarmManager.setExact(
            AlarmManager.RTC_WAKEUP,
            triggerTime,
            pendingIntent
        )
    }
}
