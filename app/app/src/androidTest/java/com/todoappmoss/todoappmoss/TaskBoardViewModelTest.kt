package com.todoappmoss.todoappmoss.ui.taskboard

import android.util.Log
import androidx.lifecycle.Observer
import com.todoappmoss.todoappmoss.data.model.Task
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test

class TaskBoardViewModelTest {

    private lateinit var viewModel: TaskBoardViewModel

    @Before
    fun setUp() {
        Log.d("TaskBoardViewModelTest", "Setting up the test environment")
        viewModel = TaskBoardViewModel()
        Log.d("TaskBoardViewModelTest", "TaskBoardViewModel initialized")
    }

    @Test
    fun testLoadTasks() {
        Log.d("TaskBoardViewModelTest", "Running testLoadTasks")

        val observer = Observer<List<Task>> { tasks ->
            Log.d("TaskBoardViewModelTest", "Observed tasks: $tasks")
            assertNotNull(tasks)
            assertTrue(tasks.isNotEmpty())
        }

        // Observer im Main-Thread hinzufügen und die Methode aufrufen
        viewModel.tasks.observeForever(observer)

        // Tasks laden und sicherstellen, dass sie korrekt geladen werden
        viewModel.loadTasks()

        val tasks = viewModel.tasks.value
        Log.d("TaskBoardViewModelTest", "Loaded tasks: $tasks")
        assertNotNull(tasks)
        assertTrue(tasks!!.isNotEmpty())

        // Observer entfernen
        viewModel.tasks.removeObserver(observer)
    }

    @Test
    fun testUpdateTask() {
        Log.d("TaskBoardViewModelTest", "Running testUpdateTask")

        viewModel.loadTasks()

        val initialTasks = viewModel.tasks.value
        assertNotNull(initialTasks)
        assertTrue(initialTasks!!.isNotEmpty())

        val task = initialTasks.first().copy(isCompleted = true)
        viewModel.updateTask(task)

        val updatedTasks = viewModel.tasks.value
        val updatedTask = updatedTasks?.find { it.id == task.id }
        assertNotNull(updatedTask)
        assertTrue(updatedTask!!.isCompleted)
        Log.d("TaskBoardViewModelTest", "Task update successful: $updatedTask")
    }
}
