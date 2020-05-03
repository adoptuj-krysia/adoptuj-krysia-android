package pl.tuchola.zslit.krychu.logic
import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import androidx.core.app.NotificationManagerCompat
import pl.tuchola.zslit.krychu.R
import java.util.concurrent.atomic.AtomicInteger

class NotificationManager(private val context: Context) {
    private object NotificationID {
        private val c: AtomicInteger = AtomicInteger(0)
        val id: Int
            get() = c.incrementAndGet()
    }

    fun showNotification(notification: Notification) {
        with(NotificationManagerCompat.from(context)) {
            notify(NotificationID.id, notification)
        }
    }

    fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val name = context.getString(R.string.notification_channel_name)
            val descriptionText = context.getString(R.string.notification_channel_description)
            val importance = NotificationManager.IMPORTANCE_DEFAULT
            val channel = NotificationChannel(context.getString(R.string.notification_channel_id), name, importance).apply {
                description = descriptionText
            }
            val notificationManager: NotificationManager =
                context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            notificationManager.createNotificationChannel(channel)
        }
    }
}