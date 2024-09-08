package com.todoappmoss.todoappmoss

import ApiClient
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.ext.junit.rules.ActivityScenarioRule
import at.fhjoanneum.todoappmoss.SplashActivity
import com.todoappmoss.todoappmoss.ui.edittask.EditTaskViewModel
import com.todoappmoss.todoappmoss.data.model.Task
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class EditTaskViewModelInstrumentationTest {

    private lateinit var viewModel: EditTaskViewModel

    private lateinit var apiClient: ApiClient

    @get:Rule
    val activityRule = ActivityScenarioRule(SplashActivity::class.java)

    @Before
    fun setUp() {
        viewModel = EditTaskViewModel()
        apiClient = ApiClient()
    }

    @Test
    fun testUpdateTask() = runBlocking {
        val task = Task(1, "Test Task", "Test Description", "2024-09-08", false, 1)

        val result = viewModel.updateTask(task)

        Assert.assertTrue(result)
    }

    @Test
    fun testDeleteTask() = runBlocking {

        val task = Task(1000, "Test Task", "Test Description", "2024-09-09", false, 1)

        apiClient.postTask(task)

        val taskId = 1000

        val result = viewModel.deleteTask(taskId)

        Assert.assertTrue(result)
    }
}
