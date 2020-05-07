package pl.tuchola.zslit.krychu.widgets
import android.appwidget.AppWidgetManager
import android.appwidget.AppWidgetProvider
import android.content.ComponentName
import android.content.Context
import android.content.Intent

class WidgetUpdateRequest<T> (
    private val context: Context,
    private val widgetProvider: Class<T>
) where T : AppWidgetProvider {

    fun requestUpdate() {
        val intent = Intent(context, widgetProvider)
        intent.action = "android.appwidget.action.APPWIDGET_UPDATE"
        val name = ComponentName(context, widgetProvider)

        val ids = AppWidgetManager.getInstance(context).getAppWidgetIds(name)
        intent.putExtra(AppWidgetManager.EXTRA_APPWIDGET_IDS, ids)
        context.sendBroadcast(intent)
    }

}
