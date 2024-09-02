package com.example.todoappmoss.bloc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.example.todoappmoss.data.model.Task

class BootReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        if (Intent.ACTION_BOOT_COMPLETED == intent.action) {
            val tasks = loadTasksFromDatabase()
            TaskUtils.scheduleAllNotifications(context, tasks)
        }
    }

    private fun loadTasksFromDatabase(): List<Task> {
        return listOf()
    }
}
