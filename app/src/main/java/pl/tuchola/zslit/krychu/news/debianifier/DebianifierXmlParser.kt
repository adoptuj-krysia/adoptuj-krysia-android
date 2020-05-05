package pl.tuchola.zslit.krychu.news.debianifier
import java.io.InputStream

class DebianifierXmlParser(val xmlStream: InputStream) {

    public fun getPatternCollection() : DebianifierPatternCollection {
        return DebianifierPatternCollection()
    }

}