package pl.tuchola.zslit.krychu.files
import android.content.Context
import java.io.File

class LastWidgetTextFile(private val context: Context) {

    private val lastTextFile =
        File(context.getExternalFilesDir(null)!!.absolutePath + "/last_widget_text.txt")

    fun writeLastText(lastText: String) {
        try {
            lastTextFile.parentFile?.mkdirs()
            lastTextFile.writeText(lastText)
        } catch(e: Exception) {}
    }

    fun getLastText() : String? {
        return try {
            if(!lastTextFile.exists())
                null
            else
                lastTextFile.readText()
        } catch(e: Exception) {
            null
        }
    }

}