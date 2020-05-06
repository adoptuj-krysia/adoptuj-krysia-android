package pl.tuchola.zslit.krychu.news
import java.io.Serializable
import java.net.URL

data class News(var header: String, var body: String, var imageLink: URL? = null) : Serializable {

    val bodyShort : String
    get() = this.body.split('.').first() + "..."

}