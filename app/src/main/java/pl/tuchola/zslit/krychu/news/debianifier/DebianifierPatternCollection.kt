package pl.tuchola.zslit.krychu.news.debianifier
import pl.tuchola.zslit.krychu.news.News

class DebianifierPatternCollection/*(private val patterns: Array<DebianifierPattern>)*/ {

    //Zwraca listę newsów: jeśli news z argumentu pasuje do któregoś wzorca, nakłada wzorzec na news
    //W przeciwnym wypadku używany jest DefaultDebianifier
    fun processNews(news: List<News>) : List<News> {
        return listOf()
    }

}