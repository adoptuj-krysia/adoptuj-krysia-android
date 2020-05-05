package pl.tuchola.zslit.krychu.news.debianifier

class DebianifierPattern(headerRule: ReplaceRule, bodyRules: Array<ReplaceRule>) {
    class ReplaceRule(public val from: String, public val to: String)
}