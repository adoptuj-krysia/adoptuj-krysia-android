package pl.tuchola.zslit.krychu.news.debianifier
import org.xml.sax.Attributes
import org.xml.sax.helpers.DefaultHandler

class DebianifierSaxHandler: DefaultHandler() {

    private val openTags = mutableListOf<String?>()
    private val patterns = mutableListOf<DebianifierPattern>()
    private val headerShouldContain = mutableListOf<String>()
    private val bodyShouldContain = mutableListOf<String>()
    private val replaceHeaderRules = mutableListOf<DebianifierPattern.ReplacementRule>()
    private val replaceBodyRules = mutableListOf<DebianifierPattern.ReplacementRule>()

    fun getParsedCollection() : DebianifierPatternCollection {
        return DebianifierPatternCollection(patterns.toTypedArray())
    }

    override fun startDocument() {
        openTags.clear(); headerShouldContain.clear()
        patterns.clear(); bodyShouldContain.clear()
        replaceHeaderRules.clear()
        replaceBodyRules.clear()
    }

    override fun characters(ch: CharArray?, start: Int, length: Int) {
        if("string" in openTags && "activation-rules" in openTags && ch != null) {
            if("header-should-contain" in openTags)
                headerShouldContain.add(String(ch))
            else if("body-should-contain" in openTags)
                bodyShouldContain.add(String(ch))
        }
    }

    override fun startElement(uri: String?, localName: String?, qName: String?, attributes: Attributes?) {
        openTags.add(localName)

        if("replacement-rule" in openTags && attributes != null) {
            if("replace-header" in openTags)
                replaceHeaderRules.add(DebianifierPattern.ReplacementRule(attributes.getValue("from"), attributes.getValue("to")))
            else if("replace-body" in openTags)
                replaceBodyRules.add(DebianifierPattern.ReplacementRule(attributes.getValue("from"), attributes.getValue("to")))
        }
    }

    override fun endElement(uri: String?, localName: String?, qName: String?) {
        openTags.removeAt(openTags.lastIndexOf(localName))

        if(localName == "pattern") {
            patterns.add(DebianifierPattern(
                DebianifierPattern.ActivationRules(headerShouldContain.toTypedArray(), bodyShouldContain.toTypedArray()),
                DebianifierPattern.ExecutionRules(replaceHeaderRules.toTypedArray(), replaceBodyRules.toTypedArray())
            ))

            replaceBodyRules.clear()
            replaceHeaderRules.clear()
            headerShouldContain.clear()
            bodyShouldContain.clear()
        }
    }

}