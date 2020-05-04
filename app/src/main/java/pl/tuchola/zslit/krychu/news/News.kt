package pl.tuchola.zslit.krychu.news
import java.io.Serializable
import java.net.URL

data class News(val header: String, val bodyLong: String, val imageLink: URL? = null) : Serializable {

    val bodyShort : String
    get() = this.bodyLong.split('.').first() + "..."

}