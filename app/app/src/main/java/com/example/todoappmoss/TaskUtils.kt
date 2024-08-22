import android.util.Log
import java.time.LocalDateTime
import java.time.LocalTime
import java.time.format.DateTimeFormatter
import java.time.format.DateTimeParseException
import java.util.Locale

object TaskUtils {

    fun parseReminderTime(reminder: String): String? {
        return when (reminder) {
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

    fun parseDeadline(date: String, time: String): String {
        val formatter = DateTimeFormatter.ofPattern("d/M/yyyy HH:mm", Locale.getDefault())
        return LocalDateTime.parse("$date $time", formatter).toString()
    }
}
