package pl.tuchola.zslit.krychu.io
import android.content.Context
import java.io.File

class LastWidgetTextFile(private val context: Context) {

    private val lastTextFile =
        File(context.getExternalFilesDir(null)!!.absolutePath + "/last_widget_text.txt")

    fun writeLastText(lastText: String) {
        lastTextFile.parentFile?.mkdirs()
        lastTextFile.writeText(lastText)
    }

    fun getLastText() : String? {
        return if(!lastTextFile.exists())
            null
        else
            lastTextFile.readText()
    }

}