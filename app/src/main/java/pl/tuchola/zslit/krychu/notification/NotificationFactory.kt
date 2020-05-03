package pl.tuchola.zslit.krychu.notification
import android.app.Notification
import android.content.Context
import androidx.core.app.NotificationCompat
import pl.tuchola.zslit.krychu.R

class NotificationFactory(private val context: Context) {

    fun getChristmasNotification() : Notification {
        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.icon_snow)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_body_christmas))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

    fun getEasterNotification() : Notification {
        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.icon_easter)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_body_easter))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

    fun getBirthdayNotification() : Notification {
        val builder = NotificationCompat.Builder(context, context.getString(R.string.notification_channel_id))
            .setSmallIcon(R.drawable.icon_gift)
            .setContentTitle(context.getString(R.string.notification_title))
            .setContentText(context.getString(R.string.notification_body_birthday))
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
        return builder.build()
    }

}