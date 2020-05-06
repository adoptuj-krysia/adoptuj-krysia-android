package pl.tuchola.zslit.krychu.news.debianifier

import android.util.Log
import pl.tuchola.zslit.krychu.news.News

class DebianifierPattern(activation_rules: ActivationRules, execution_rules: ExecutionRules) {

    private val activationRules: ActivationRules = activation_rules

    private val executionRules: ExecutionRules = execution_rules

    class ReplacementRule(replaceFrom: String, replaceTo: String) {
        val from: String = replaceFrom
        val to: String = replaceTo
    }

    class ActivationRules(mHeaderShouldContain: Array<String> = arrayOf(), mBodyShouldContain: Array<String> = arrayOf()) {
        val headerShouldContain = mHeaderShouldContain
        val bodyShouldContain = mBodyShouldContain
    }

    class ExecutionRules(mHeaderReplacementRules: Array<ReplacementRule> = arrayOf(), mBodyReplacementRules: Array<ReplacementRule> = arrayOf()) {
        val headerReplacementRules = mHeaderReplacementRules
        val bodyReplacementRules = mBodyReplacementRules
    }

    fun matches(news: News) : Boolean {
        if(activationRules.headerShouldContain.isEmpty() && activationRules.bodyShouldContain.isEmpty())
            return false
        for(str in activationRules.headerShouldContain) {
            if(str !in news.header)
                return false
        }
        for(str in activationRules.bodyShouldContain) {
            if(str !in news.body)
                return false
        }
        return true
    }

    fun tryExecute(news: News) {
        if(!matches(news)) return
        forceExecute(news)
    }

    fun forceExecute(news: News) {
        executionRules.bodyReplacementRules.forEach {
            news.body = news.body.replace(it.from, it.to)
        }
        executionRules.headerReplacementRules.forEach {
            news.header = news.header.replace(it.from, it.to)
        }
    }

}