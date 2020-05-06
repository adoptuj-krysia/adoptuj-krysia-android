package pl.tuchola.zslit.krychu.news.debianifier
import android.util.Log
import pl.tuchola.zslit.krychu.news.News

class DebianifierPatternCollection(val patterns: Array<DebianifierPattern>) {

    //Jeśli news pasuje do istniejącego wzorca debianifikacji, nakłada wzorzec na newsa
    //Jeżeli do danego newsa nie pasuje żaden wzorzec, używany jest DefaultDebianifier
    fun debianifyNews(news: News) {
        var newsDebianified = false
        patterns.forEach {
            if(it.matches(news)) {
                it.forceExecute(news)
                newsDebianified = true
                return
            }
        }
        if(!newsDebianified) {
            DefaultDebianifier(news).debianifyNews()
        }
    }

}