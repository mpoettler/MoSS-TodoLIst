import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.util.Log
import at.fhjoanneum.todoappmoss.bloc.NotificationReceiver
import com.todoappmoss.todoappmoss.data.model.Task
import java.text.SimpleDateFormat
import java.time.LocalDateTime
import java.time.format.DateTimeFormatter
import java.util.Calendar
import java.util.Date
import java.util.Locale

object TaskUtils {

    fun parseReminderTime(reminder: String): String? {
        return when (reminder) {
            "10 Minuten vorher" -> "00:10:00"
            "30 Minuten vorher" -> "00:30:00"
            "1 Stunde vorher" -> "01:00:00"
            "1 Tag vorher" -> "24:00:00"
            else -> null
        }
    }

    private fun formatTimeFromNow(seconds: Int): String {
        val now = LocalDateTime.now()
        val reminderTime = now.plusSeconds(seconds.toLong())
        return reminderTime.toString()
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


    fun parseDeadline(date: String, time: String): String {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm", Locale.getDefault())
        return LocalDateTime.parse("$date $time", formatter).toString()
    }

    private fun parseDuration(duration: String): Int? {
        val parts = duration.split(":").map { it.toIntOrNull() }
        return if (parts.size == 3 && parts[0] != null && parts[1] != null && parts[2] != null) {
            (parts[0]!! * 3600) + (parts[1]!! * 60) + parts[2]!!
        } else {
            null
        }
    }

    fun scheduleAllNotifications(context: Context, tasks: List<Task>) {
        val dateFormatter = SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss", Locale.getDefault())

        for (task in tasks) {
            task.reminderTime?.let { reminderTime ->
                try {
                    val deadlineDate: Date? = dateFormatter.parse(task.deadline)
                    if (deadlineDate != null) {
                        val calendar = Calendar.getInstance()
                        calendar.time = deadlineDate

                        val reminderDuration = parseDuration(reminderTime)
                        if (reminderDuration != null) {
                            calendar.add(Calendar.SECOND, -reminderDuration)
                            scheduleNotification(context, task, calendar.timeInMillis)
                        }
                        else {
                            Log.e("NotificationScheduler", "Reminderduration is null: ${reminderDuration}")
                        }
                    } else {
                        Log.e("NotificationScheduler", "deadlineDate is null: ${task.deadline}")
                    }
                } catch (e: Exception) {
                    e.printStackTrace()
                    Log.e("NotificationScheduler", "Fehler beim Parsen des Datums: ${task.deadline}")
                }
            }
        }
    }



}
