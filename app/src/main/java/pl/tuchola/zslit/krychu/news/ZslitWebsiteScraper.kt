package pl.tuchola.zslit.krychu.news
import android.os.AsyncTask
import org.jsoup.HttpStatusException
import org.jsoup.Jsoup
import pl.tuchola.zslit.krychu.common.NetworkDataProvider
import java.io.IOException
import java.net.URL


class ZslitWebsiteScraper() : NetworkDataProvider<News, ZslitConnectionError> {

    companion object {
        private const val NEWS_PAGE = "http://zslit-tuchola.pl/blog/page/CURR__PAGE"
    }
    private val currentPageLink
        get() = NEWS_PAGE.replace("CURR__PAGE", currentPage.toString())
    private val nextPageLink
        get() = NEWS_PAGE.replace("CURR__PAGE", (currentPage+1).toString())

    private var currentPage: Int = 1
    private var articleLinksOnCurrentPage: List<URL>? = null
    private var newsIndexOnCurrentPage: Int = -1


    override fun startFetching(onSuccess: (News) -> Unit, onError: (ZslitConnectionError) -> Unit) {
        AsyncTask.execute {
            try {
                if(articleLinksOnCurrentPage == null)
                    articleLinksOnCurrentPage = getNewsUrlOnCurrentPage()
                newsIndexOnCurrentPage++

                //jeśli obecna strona ma następnego newsa, to..
                if(articleLinksOnCurrentPage!!.elementAtOrNull(newsIndexOnCurrentPage) != null) {
                    var news = resolveUrlToNews(articleLinksOnCurrentPage!![newsIndexOnCurrentPage])
                    onSuccess(news)
                }
                //jeśli obecna strona nie ma następnego newsa, to..
                else {
                    //jeśli następna strona jest dostępna, to..
                    if(hasNextPage()) {
                        currentPage++
                        newsIndexOnCurrentPage = -1
                        articleLinksOnCurrentPage = getNewsUrlOnCurrentPage()
                        startFetching(onSuccess, onError)
                    }
                    //jeśli następna strona nie jest dostępna, to..
                    else {
                        onError(ZslitConnectionError.NO_NEWS_AVAILABLE)
                    }
                }
            } catch(e: IOException) {
                onError(ZslitConnectionError.INTERNET_ERROR)
            } catch(e: NullPointerException) {
                onError(ZslitConnectionError.INVALID_SERVER_RESPONSE)
            }
            catch(e: Exception) {
                onError(ZslitConnectionError.UNRECOGNIZED_ERROR)
            }
        }
    }

    private fun hasNextPage() : Boolean {
        return try {
            val doc = Jsoup.connect(nextPageLink).timeout(8000).execute()
            doc.statusCode() == 200
        } catch(e: HttpStatusException) {
            false
        } catch(e: Exception) {
            throw e
        }
    }

    private fun getNewsUrlOnCurrentPage() : List<URL> {
        val doc = Jsoup.connect(currentPageLink).timeout(8000).get()
        val urls = ArrayList<URL>()
        doc.select(".read-more a").forEach {
            urls.add(URL(it.attr("abs:href")))
        }
        return urls
    }

    private fun resolveUrlToNews(articleLink: URL) : News {
        val doc = Jsoup.connect(articleLink.toString()).timeout(8000).get()
        val header = doc.select("h1.entry-title").first().text()
        val bodyElement = doc.select(".entry-content").first()
        bodyElement.select("img").remove()
        val body = bodyElement.html().replace(Regex("(?=<!--)([\\s\\S]*?)-->"), "")
        val link = doc.body().select("img.attachment-large").attr("src")
        return News(header, body, URL(link))
    }

}
