package pl.tuchola.zslit.krychu.notification
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.io.ActivityLog
import pl.tuchola.zslit.krychu.utils.EasterDate
import java.util.*

class NotificationBroadcastReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        ActivityLog(context).writeLine(context.getString(R.string.log_notification_broadcast_received))
        val factory = NotificationFactory(context)
        val manager = NotificationManager(context)
        val calendar = Calendar.getInstance()
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val easterCalendar = Calendar.getInstance()
        easterCalendar.time = EasterDate(calendar.get(Calendar.YEAR)).getEasterSundayDate()

        if(currentDay == 24 && currentMonth == Calendar.DECEMBER)
            manager.showNotification(factory.getChristmasNotification())
        else if(easterCalendar.get(Calendar.MONTH) == currentMonth && easterCalendar.get(Calendar.DAY_OF_MONTH) == currentDay) {
            manager.showNotification(factory.getEasterNotification())
        }
        else if(currentDay == 21 && currentMonth == Calendar.MAY) {
            manager.showNotification(factory.getBirthdayNotification())
        }
    }

}