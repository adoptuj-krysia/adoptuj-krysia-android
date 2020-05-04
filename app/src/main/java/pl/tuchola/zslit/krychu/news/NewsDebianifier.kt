package pl.tuchola.zslit.krychu.news
import kotlin.random.Random

class NewsDebianifier(private val news: News) {

    private val rand = Random(news.bodyLong.hashCode().toLong() + news.bodyShort.hashCode().toLong())
    companion object {
        const val DEBIANIFIER_WORD = "Debian"
    }

    fun getDebianifiedNews() : News {
        return News(debianifyHeader(), debianifyContent(), news.imageLink)
    }

    private fun debianifyHeader() : String {
        val randomWord = news.header.split(' ').random(rand)
        return news.header.replace(randomWord, DEBIANIFIER_WORD)
    }

    private fun debianifyContent() : String {
        var debianifiedContent = ""
        val splittedSentences = news.bodyLong.split('.')
        for(sentence in splittedSentences) {
            val splittedWords = sentence.split(' ').filter {it -> it != ""}
            debianifiedContent += if(splittedWords.size >= 3) {

                var tryCount = -1
                var randomWord: String
                do {
                    randomWord = splittedWords.random(rand);
                } while(tryCount++ < 6 || randomWord.contains('>'))

                sentence.replace(randomWord, DEBIANIFIER_WORD) + "."
            } else {
                "$sentence."
            }
        }
        return debianifiedContent
    }

}