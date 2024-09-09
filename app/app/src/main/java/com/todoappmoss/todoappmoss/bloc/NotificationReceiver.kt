package at.fhjoanneum.todoappmoss.bloc

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat

class NotificationReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context?, intent: Intent?) {
        val title = intent?.getStringExtra("task_title")
        val description = intent?.getStringExtra("task_description")

        val notificationBuilder = NotificationCompat.Builder(context!!, "task_channel")
            //.setSmallIcon(R.drawable.ic_notification)
            .setContentTitle(title)
            .setContentText(description)
            .setPriority(NotificationCompat.PRIORITY_HIGH)
            .setAutoCancel(true)

        val notificationManager = NotificationManagerCompat.from(context)
        //notificationManager.notify(1, notificationBuilder.build())
    }


}
