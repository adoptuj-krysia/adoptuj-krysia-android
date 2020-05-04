package pl.tuchola.zslit.krychu.news

class Debianifier(private val newsHeader: String, private val newsContent: String) {

    companion object {
        const val DEBIANIFIER_WORD = "Debian"
    }

    fun debianifyHeader() : String {
        val randomWord = newsHeader.split(' ').random()
        return newsHeader.replace(randomWord, DEBIANIFIER_WORD)
    }

    fun debianifyContent() : String {
        var debianifiedContent = ""
        val splittedSentences = newsContent.split('.')
        for(sentence in splittedSentences) {
            val splittedWords = sentence.split(' ').filter {it -> it != ""}
            debianifiedContent += if(splittedWords.size >= 3) {
                sentence.replace(splittedWords.random(), DEBIANIFIER_WORD)
            } else {
                sentence
            }
            debianifiedContent += "."
        }
        return debianifiedContent
    }

}