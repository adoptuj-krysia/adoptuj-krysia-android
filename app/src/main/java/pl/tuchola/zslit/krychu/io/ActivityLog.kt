package pl.tuchola.zslit.krychu.io
import android.content.Context
import android.text.format.DateFormat
import android.util.Log
import java.io.File
import java.lang.Exception
import java.util.*

class ActivityLog(private val context: Context) {

    private val logFile : File
    get() {
        return File(context.getExternalFilesDir(null)!!.absolutePath + "/activity_log.txt")
    }

    fun writeLine(message: String) {
        Thread {
            try {
                logFile.parentFile?.mkdirs()
                val date = DateFormat.format("yyyy-MM-dd kk:mm:ss", Date())
                logFile.appendText("[$date] $message\n")
                Log.d("activity-log", message)
            } catch(e:Exception) {}
        }.run()
    }

}