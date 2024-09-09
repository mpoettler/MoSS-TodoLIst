    package at.fhjoanneum.todoappmoss

    import android.content.Intent
    import android.os.Bundle
    import android.widget.CalendarView
    import android.widget.ImageView
    import androidx.appcompat.app.AppCompatActivity
    import androidx.lifecycle.Observer
    import androidx.lifecycle.ViewModelProvider
    import androidx.recyclerview.widget.LinearLayoutManager
    import androidx.recyclerview.widget.RecyclerView
    import com.todoappmoss.todoappmoss.adapter.ToDoItemAdapter
    import com.todoappmoss.todoappmoss.data.model.Task
    import com.todoappmoss.todoappmoss.ui.calendar.CalendarViewModel
    import java.util.*

    class CalendarActivity : AppCompatActivity() {

        lateinit var viewModel: CalendarViewModel
        private lateinit var adapter: ToDoItemAdapter

        override fun onCreate(savedInstanceState: Bundle?) {
            super.onCreate(savedInstanceState)
            setContentView(R.layout.activity_calendar)

            setupNavigationButtons()

            viewModel = ViewModelProvider(this).get(CalendarViewModel::class.java)

            val recyclerView = findViewById<RecyclerView>(R.id.taskRecyclerView)
            adapter = ToDoItemAdapter(emptyList(), ::onTaskClicked, ::onCheckboxChecked)
            recyclerView.adapter = adapter
            recyclerView.layoutManager = LinearLayoutManager(this)

            viewModel.selectedDate.observe(this, Observer { date ->
                viewModel.loadTasksForDate(date)
            })

            viewModel.tasksForDate.observe(this, Observer { tasks ->
                updateTasksRecyclerView(tasks)
            })

            val calendarView: CalendarView = findViewById(R.id.calendarView)
            calendarView.setOnDateChangeListener { _, year, month, dayOfMonth ->
                val selectedDate = Calendar.getInstance().apply {
                    set(year, month, dayOfMonth)
                }.time
                viewModel.selectDate(selectedDate)
            }
        }

        // Handle click on a task item
        private fun onTaskClicked(task: Task) {
            val intent = Intent(this, EditTaskActivity::class.java).apply {
                putExtra("task", task)
            }
            startActivity(intent)
        }

        // Set up navigation buttons
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

        // Update the RecyclerView with new task data
        private fun updateTasksRecyclerView(tasks: List<Task>) {
            adapter.updateData(tasks)
        }

        // Handle checkbox state changes
        private fun onCheckboxChecked(task: Task, isChecked: Boolean) {
            viewModel.updateTaskCompletion(task, isChecked)
        }
    }
