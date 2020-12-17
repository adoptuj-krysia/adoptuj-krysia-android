package pl.tuchola.zslit.krychu.widgets
import android.app.PendingIntent
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.Context
import android.content.Intent
import android.widget.RemoteViews
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.files.ActivityLog
import pl.tuchola.zslit.krychu.files.LastWidgetTextFile
import pl.tuchola.zslit.krychu.notification.NotificationAlarmSetter

class MainWidgetProvider : AppWidgetProvider() {

    companion object {
        private const val ACTION_REFRESH_REQUESTED = "pl.tuchola.zslit.krychu.APPWIDGET_UPDATE_USER_REQUESTED"
    }

    override fun onUpdate(context: Context, appWidgetManager: AppWidgetManager, appWidgetIds: IntArray) {
        NotificationAlarmSetter(context.applicationContext).startAlarm()
        for (appWidgetId in appWidgetIds) {
            val widgetEntry = WidgetEntryFactory(context).getWidgetEntry()
            val views = RemoteViews(context.packageName, R.layout.widget_main)

            LastWidgetTextFile(context).writeLastText(widgetEntry.widgetMessage)
            views.setTextViewText(R.id.widgetMessage_textView, widgetEntry.widgetMessage)
            views.setImageViewResource(R.id.widgetImage_imageView, widgetEntry.imageID)

            val intent = Intent(context, MainWidgetProvider::class.java)
            intent.action = ACTION_REFRESH_REQUESTED
             val pendingIntentButton = PendingIntent.getBroadcast(context, 0, intent, PendingIntent.FLAG_UPDATE_CURRENT)
            views.setOnClickPendingIntent(R.id.widgetImage_imageView, pendingIntentButton)

            appWidgetManager.updateAppWidget(appWidgetId, views)
            ActivityLog(context).writeLine(context.getString(R.string.log_widget_refreshed))
        }
    }

    override fun onReceive(context: Context?, intent: Intent?) {
        super.onReceive(context, intent)

        //Użytkownik poprosił o ręczne odświeżenie
        if (ACTION_REFRESH_REQUESTED == intent?.action) {
            ActivityLog(context!!).writeLine(context.getString(R.string.log_widget_refreshed_user))
            UserUpdateRequest(context).callUpdateRequest()
        }
    }
}