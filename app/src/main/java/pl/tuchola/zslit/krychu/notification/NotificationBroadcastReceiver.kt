package pl.tuchola.zslit.krychu.logic
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val factory = NotificationFactory(context)
        val manager = NotificationManager(context)
        manager.showNotification(factory.getChristmasNotification())
    }

}