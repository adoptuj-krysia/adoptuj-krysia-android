package pl.tuchola.zslit.krychu.view
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.io.ActivityLog

class DeviceBootCompletedReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        ActivityLog(context!!).writeLine(context.getString(R.string.log_device_boot_completed))
    }

}