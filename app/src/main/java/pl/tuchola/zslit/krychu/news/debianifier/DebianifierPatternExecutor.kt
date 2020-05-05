package pl.tuchola.zslit.krychu.news.debianifier
import pl.tuchola.zslit.krychu.news.News

class DebianifierPatternExecutor(val pattern: DebianifierPattern) {

    //Zwraca informację, czy przekazany news pasuje do wzorca z konstruktora
    fun matchesPattern(news: News) : Boolean {
        return false
    }

    //Jeżeli przekazany news pasuje do wzorca z konstruktora, zwraca nowy news, po wykonaniu zmian ze wzorca
    //Jeżeli przekazany news nie pasuje do wzorca, zwraca news bez zmian
    fun execute(news: News) : News {
        return news
    }

}