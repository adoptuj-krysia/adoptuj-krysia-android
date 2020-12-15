package pl.tuchola.zslit.krychu.news
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler
import pl.tuchola.zslit.krychu.common.Boast
import java.net.URL

class NewsSaxHandler: DefaultHandler() {

    private val openTags = mutableListOf<String?>()

    private var currentTitle: String = "";
    private var currentShortDescription: String = "";
    private var currentHtmlContent: String = "";
    private var currentImageUrl: String = "";

    private val parsedNews = mutableListOf<News>();

    fun getParsedCollection() : List<News> {
        return parsedNews
    }

    override fun startDocument() {
        openTags.clear();
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        if(ch == null) {
            return;
        }

        val asString =  String(ch!!, start, length)
        if("title" in openTags) {
            currentTitle += asString
        }
        if("short-description" in openTags) {
            currentShortDescription += asString
        }
        if("html-content" in openTags) {
            currentHtmlContent += asString
        }
        if("image-url" in openTags) {
            currentImageUrl += asString
        }
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        openTags.add(localName)
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        openTags.removeAt(openTags.lastIndexOf(localName))

        if(localName == "news") {
            parsedNews.add(News(currentTitle, currentShortDescription, currentHtmlContent.trim(), URL(currentImageUrl)))

            currentTitle = ""
            currentShortDescription = ""
            currentHtmlContent = ""
            currentImageUrl = ""
        }
    }

}
