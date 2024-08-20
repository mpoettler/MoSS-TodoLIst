import android.util.Log
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object TaskUtils {

    fun parseReminderTime(reminder: String?): String? {
        return reminder?.let {
            when {
                it.contains("Minute") -> {
                    val minutesBefore = it.split(" ")[0].toInt()
                    formatTimeFromNow(minutesBefore * 60)
                }
                it.contains("Stunde") -> {
                    val hoursBefore = it.split(" ")[0].toInt()
                    formatTimeFromNow(hoursBefore * 60 * 60)
                }
                it.contains("Tag") -> {
                    val daysBefore = it.split(" ")[0].toInt()
                    formatTimeFromNow(daysBefore * 24 * 60 * 60)
                }
                else -> {
                    try {
                        val parsedTime = LocalTime.parse(it, DateTimeFormatter.ofPattern("HH:mm"))
                        parsedTime.toString()  // Konvertiere ins erwartete Format
                    } catch (e: DateTimeParseException) {
                        Log.e("TaskUtils", "Invalid time format for reminder: $it", e)
                        null
                    }
                }
            }
        }
    }

    private fun formatTimeFromNow(seconds: Int): String {
        val now = LocalDateTime.now()
        val reminderTime = now.plusSeconds(seconds.toLong())
        return reminderTime.toString()
    }

    fun parseDeadline(date: String, time: String): String {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm", Locale.getDefault())
        return LocalDateTime.parse("$date $time", formatter).toString()
    }
}
