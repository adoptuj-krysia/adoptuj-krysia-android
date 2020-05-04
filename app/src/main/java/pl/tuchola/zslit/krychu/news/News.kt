package pl.tuchola.zslit.krychu.news
import java.io.Serializable

data class News(val newsIndex: Int, val header: String, val bodyShort: String, val bodyLong: String) : Serializable