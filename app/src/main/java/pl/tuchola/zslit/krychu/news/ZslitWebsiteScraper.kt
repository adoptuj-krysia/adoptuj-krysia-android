package pl.tuchola.zslit.krychu.news
import android.os.AsyncTask
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import java.net.URL


class ZslitWebsiteScraper() {

    fun resolveUrlToNews(newsIndex: Int, articleLink: URL, onSuccess: (News) -> Unit, onError: (ZslitConnectionError) -> Unit) {
        try {
            AsyncTask.execute {
                try {
                    val doc = Jsoup.connect(articleLink.toString()).timeout(8000).get()
                    val header = doc.select("h1.entry-title").first().text()
                    val body = doc.select(".entry-content").first().html()

                    onSuccess(News(newsIndex, header, body!!))
                } catch(e: Exception) {
                    onError(ZslitConnectionError.UNRECOGNIZED_ERROR)
                    throw e
                }
            }
        } catch(e: Exception) {
            onError(ZslitConnectionError.UNRECOGNIZED_ERROR)
            throw e
        }
    }

}
