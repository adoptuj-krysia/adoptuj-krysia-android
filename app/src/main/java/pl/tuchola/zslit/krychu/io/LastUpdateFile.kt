package pl.tuchola.zslit.krychu.io
import android.content.Context
import java.io.File
import java.text.SimpleDateFormat
import java.util.*

class LastUpdateFile(private val context: Context) {

    private val dateFormat = SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH)

    private val lastRefreshFile: File =
        File(context.getExternalFilesDir(null)!!.absolutePath + "/last_refresh.txt")

    //Druga linijka pliku, oznaczająca ile razy użytkownik odświeżył widget w ciągu ostatnich 5 minut
    fun getLastRefreshCount(): Int? {
        if(!lastRefreshFile.exists())
            return null;

        val text = lastRefreshFile.readLines()
        if(text.size < 2)
            return null

        return text[1].toIntOrNull()
    }

    //Pierwsza linijka pliku, oznaczająca o której godzinie użytkownik otatnio ręcznie odświeżył widget
    fun getLastRefreshDate(): Date? {
        if(!lastRefreshFile.exists())
            return null;

        val text = lastRefreshFile.readLines()
        if(text.size < 2)
            return null

        return dateFormat.parse(text[0])
    }

    fun writeData(refreshCount: Int?, refreshDate: Date = Date()) {
        lastRefreshFile.parentFile?.mkdirs()
        lastRefreshFile.writeText(dateFormat.format(refreshDate) + "\n" + (refreshCount ?: 0))
    }

}