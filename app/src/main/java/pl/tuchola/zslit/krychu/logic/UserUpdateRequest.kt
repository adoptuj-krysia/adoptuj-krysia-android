package pl.tuchola.zslit.krychu.logic
import android.content.Context
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.io.LastUpdateFile
import pl.tuchola.zslit.krychu.io.ActivityLog
import pl.tuchola.zslit.krychu.utils.Boast
import pl.tuchola.zslit.krychu.utils.WidgetUpdateRequest
import pl.tuchola.zslit.krychu.view.MainWidgetProvider
import java.util.*
import java.util.concurrent.TimeUnit

class UserUpdateRequest(private val context: Context) {

    //Konfiguracja odświeżania przez użytkownika
    companion object {
        //Ile maksymalnie odświeżeń można wykonać pod rząd
        const val REFRESH_LIMIT = 3

        //Ile minut trzeba odczekać po przekroczeniu limitu
        const val INTERVAL_MINUTES = 5
    }

    private fun acceptRequest() {
        WidgetUpdateRequest(context!!, MainWidgetProvider::class.java).requestUpdate()
        ActivityLog(context).writeLine(context.getString(R.string.log_widget_refreshed_user_accept))
    }

    private fun declineRequest() {
        Boast.showLongMessage(context!!.getString(R.string.widget_friend_upset), context)
        ActivityLog(context).writeLine(context.getString(R.string.log_widget_refreshed_user_decline))
    }

    fun callUpdateRequest() {
        var lastRefreshed = LastUpdateFile(context).getLastRefreshDate() ?: Date(1970, 1, 1)
        var refreshCount = LastUpdateFile(context).getLastRefreshCount() ?: 0

        var diffMinutes = Date().time - lastRefreshed.time
        diffMinutes = TimeUnit.MILLISECONDS.toMinutes(diffMinutes)

        if(diffMinutes <= INTERVAL_MINUTES && refreshCount > REFRESH_LIMIT) {
            declineRequest()
            //acceptRequest() //tylko do celów testowania
        } else if(diffMinutes >= INTERVAL_MINUTES) {
            refreshCount = 1
            LastUpdateFile(context).writeData(refreshCount)
            acceptRequest()
        } else {
            refreshCount++
            LastUpdateFile(context).writeData(refreshCount)
            acceptRequest()
        }

    }
}