package pl.tuchola.zslit.krychu.news
import java.io.Serializable
import java.net.URL

data class News(var title: String, var shortDescription: String, var htmlContent: String, var imageLink: URL? = null) : Serializable {


}