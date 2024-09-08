package com.todoappmoss.todoappmoss

import androidx.lifecycle.Lifecycle
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.platform.app.InstrumentationRegistry
import at.fhjoanneum.todoappmoss.CalendarActivity
import com.todoappmoss.todoappmoss.ui.calendar.CalendarViewModel
import kotlinx.coroutines.runBlocking
import org.junit.*
import org.junit.runner.RunWith
import java.text.SimpleDateFormat
import java.util.*

@RunWith(AndroidJUnit4::class)
class CalendarViewInstrumentedTest {

    @get:Rule
    val activityRule = ActivityScenarioRule(CalendarActivity::class.java)

    @Test
    fun testSelectDateLoadsTasksForDate() {
        val scenario = activityRule.scenario

        scenario.moveToState(Lifecycle.State.RESUMED)

        scenario.onActivity { activity ->
            val viewModel = activity.viewModel

            viewModel.tasksForDate.observe(activity) { tasks ->
                assert(tasks != null)
                assert(tasks.isNotEmpty())
            }
        }
    }
}
