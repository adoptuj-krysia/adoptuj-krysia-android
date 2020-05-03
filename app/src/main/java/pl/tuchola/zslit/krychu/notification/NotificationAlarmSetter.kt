package pl.tuchola.zslit.krychu.notification

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Context.ALARM_SERVICE
import android.content.Intent
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.io.ActivityLog
import java.util.*

//Uruchamia alarm, który raz na 24 godziny wywołuje NotificationBroadcastReceiver
class NotificationAlarmSetter(private val context: Context) {

    companion object {
        const val ALARM_ID = 200
    }

    fun startAlarm() {
        if(isAlarmEnabled())
            return

        val intent = Intent(context, NotificationBroadcastReceiver::class.java)
        val pendingIntent = PendingIntent.getBroadcast(context, ALARM_ID, intent, PendingIntent.FLAG_UPDATE_CURRENT)
        val calendar = Calendar.getInstance()
        calendar.timeInMillis = System.currentTimeMillis()

        val alarmManager = context.getSystemService(ALARM_SERVICE) as AlarmManager
        alarmManager.setRepeating(
            AlarmManager.RTC_WAKEUP,
            calendar.timeInMillis+1000,
            86400000,
            pendingIntent
        )

        ActivityLog(context).writeLine(context.getString(R.string.log_notification_alarm_set_up))
    }

    private fun isAlarmEnabled() : Boolean {
        return PendingIntent.getBroadcast(
            context, ALARM_ID,
            Intent(context, NotificationBroadcastReceiver::class.java),
            PendingIntent.FLAG_NO_CREATE
        ) != null
    }

}