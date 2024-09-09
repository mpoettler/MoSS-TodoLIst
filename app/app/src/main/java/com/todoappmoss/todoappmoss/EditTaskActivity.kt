package at.fhjoanneum.todoappmoss

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import com.todoappmoss.todoappmoss.data.model.Task
import com.todoappmoss.todoappmoss.ui.edittask.EditTaskViewModel
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
        val deadlineTextView = findViewById<TextView>(R.id.editTaskDeadline)
        val repeatIntervalTextView = findViewById<TextView>(R.id.editTaskRepeatInterval)

        titleEditText.setText(task.title)
        deadlineTextView.text = task.deadline
        repeatIntervalTextView.text = task.priority

        findViewById<Button>(R.id.saveButton).setOnClickListener {
            task.title = titleEditText.text.toString()
            task.deadline = deadlineTextView.text.toString()
            task.priority = repeatIntervalTextView.text.toString()

            updateTask(task)

            val intent = Intent(this@EditTaskActivity, TaskBoardActivity::class.java)
            startActivity(intent)

            finish()
        }

        findViewById<Button>(R.id.deleteButton).setOnClickListener {
            deleteTask(task.id)
        }

        findViewById<Button>(R.id.cancelButton).setOnClickListener {
            finish()
        }



    }






    //Funktion that Updated Selected Task
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

    //Funktion that deletes Selected Task
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
