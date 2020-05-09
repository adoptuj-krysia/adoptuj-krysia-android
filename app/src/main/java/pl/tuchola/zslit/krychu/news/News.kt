package pl.tuchola.zslit.krychu.news
import android.text.Html
import androidx.core.text.HtmlCompat
import java.io.Serializable
import java.net.URL

data class News(var header: String, var body: String, var imageLink: URL? = null) : Serializable {

    val bodyShort : String
    get() = HtmlCompat.fromHtml(body, HtmlCompat.FROM_HTML_MODE_COMPACT).toString().split('.').first() + "..."

}