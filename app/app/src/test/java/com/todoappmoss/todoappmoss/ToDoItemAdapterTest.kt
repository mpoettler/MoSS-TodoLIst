package com.todoappmoss.todoappmoss

import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.todoappmoss.todoappmoss.adapter.ToDoItemAdapter
import com.todoappmoss.todoappmoss.data.model.Task
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class ToDoItemAdapterTest {

    @Test
    fun testGetItemCount() {
        val mockList = listOf(Task(1, "Test Task", "Test Description", "High", false, 1))
        val adapter = ToDoItemAdapter(mockList, {}, { _, _ -> })

        assertEquals(1, adapter.itemCount)
    }



}
