package com.todoappmoss.todoappmoss

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import com.todoappmoss.todoappmoss.adapter.ToDoItemAdapter
import com.todoappmoss.todoappmoss.data.model.Task
import org.junit.Assert.assertEquals
import org.junit.Test

class ToDoItemAdapterInstrumentedTest {

    @Test
    fun testUpdateData() {
        val context = ApplicationProvider.getApplicationContext<Context>()
        val recyclerView = RecyclerView(context)
        val initialList = listOf(Task(1, "Test Task", "Test Description", "High", false, 1))
        val adapter = ToDoItemAdapter(initialList, {}, { _, _ -> })

        recyclerView.adapter = adapter

        val newList = listOf(Task(2, "Test Task 2", "Test Description 2", "Low", false, 1))
        adapter.updateData(newList)

        assertEquals(1, adapter.itemCount)
        assertEquals("Test Description 2", adapter.todoList[0].description)
    }
}