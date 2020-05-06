package pl.tuchola.zslit.krychu.news.debianifier
import pl.tuchola.zslit.krychu.news.News
import kotlin.random.Random

class DefaultDebianifier(private val news: News) {

    private val rand = Random(news.body.hashCode().toLong() + news.bodyShort.hashCode().toLong())
    companion object {
        const val DEBIANIFIER_WORD = "Debian"
    }

    fun debianifyNews() {
        news.header = debianifyHeader()
        news.body = debianifyContent()
    }

    private fun debianifyHeader() : String {
        val onlyLongWords = news.header.split(' ').filter{it.length >= 4}
        val randomWord = if(onlyLongWords.isEmpty()) " x " else onlyLongWords.random(rand)
        return news.header.replaceFirst(randomWord,
            DEBIANIFIER_WORD
        )
    }

    private fun debianifyContent() : String {
        var debianifiedContent = ""
        val splittedSentences = news.body.split('.')
        for(sentence in splittedSentences) {
            val splittedWords = sentence.split(' ').filter {it -> it != ""}
            debianifiedContent += if(splittedWords.size >= 3) {

                var tryCount = -1
                var randomWord: String
                do {
                    val onlyLongWords = splittedWords.filter{it -> it.length >= 4}
                    randomWord = if(onlyLongWords.isEmpty())
                        " x "
                    else
                        onlyLongWords.random(rand)
                } while(tryCount++ < 6 && randomWord.contains('>') &&  randomWord.contains('<'))

                if(randomWord.contains('<') || randomWord.contains('<')) continue

                sentence.replaceFirst(randomWord,
                    DEBIANIFIER_WORD
                ) + "."
            } else {
                "$sentence."
            }
        }
        return debianifiedContent
    }

}