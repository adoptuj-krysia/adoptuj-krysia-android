package pl.tuchola.zslit.krychu.news
import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.safety.Whitelist
import pl.tuchola.zslit.krychu.utils.Boast
import java.net.URL


class ZslitWebsiteScraper() {

    companion object {
        private const val NEWS_PAGE = "http://zslit-tuchola.pl/blog/page/CURR__PAGE"
    }

    private var currentPage: Int = 1
    private var articleLinksOnCurrentPage: List<URL>? = null
    private var newsIndexOnCurrentPage: Int = 0

    private val currentPageLink
    get() = NEWS_PAGE.replace("CURR__PAGE", currentPage.toString())
    private val nextPageLink
    get() = NEWS_PAGE.replace("CURR_PAGE", (currentPage+1).toString())

    fun getNextNews(onSuccess: (News) -> Unit, onError: (ZslitConnectionError) -> Unit) {
        if(articleLinksOnCurrentPage == null)
            getNewsUrlOnCurrentPage({articleLinksOnCurrentPage = it}, {onError(it)})

        if(articleLinksOnCurrentPage!!.elementAtOrNull(newsIndexOnCurrentPage) == null) {
            var hasNextPage: Boolean


        }


    }

    fun hasNextNews() {

    }

    fun hasNextPage(onSuccess: (Boolean) -> Unit, onError: (ZslitConnectionError) -> Unit) {
        try {
            AsyncTask.execute {
                try {
                    val doc = Jsoup.connect(nextPageLink).timeout(8000).execute()
                    onSuccess(doc.statusCode() == 200)
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

    fun getNewsUrlOnCurrentPage(onSuccess: (List<URL>) -> Unit, onError: (ZslitConnectionError) -> Unit) {
        try {
            AsyncTask.execute {
                try {
                    val doc = Jsoup.connect(currentPageLink).timeout(8000).get()
                    val urls = ArrayList<URL>()
                    doc.select(".read-more a").forEach {
                        urls.add(URL(it.attr("abs:href")))
                    }
                    onSuccess(urls)
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

    fun resolveUrlToNews(articleLink: URL, onSuccess: (News) -> Unit, onError: (ZslitConnectionError) -> Unit) {
        try {
            AsyncTask.execute {
                try {
                    val doc = Jsoup.connect(articleLink.toString()).timeout(8000).get()
                    val header = doc.select("h1.entry-title").first().text()
                    val body = doc.select(".entry-content").first().html()
                    val link = doc.body().select("img.attachment-large").attr("src")

                    onSuccess(News(header, body!!, URL(link)))
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
