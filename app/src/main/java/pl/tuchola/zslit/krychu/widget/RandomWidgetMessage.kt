package pl.tuchola.zslit.krychu.widget
import android.content.Context
import pl.tuchola.zslit.krychu.files.LastWidgetTextFile

class RandomWidgetMessage(private val context: Context) {

    //Zwraca losową wiadomość widżetu z tablicy o podanym ID (losuje bez powtórzeń)
    fun getWidgetMessage(messageArrayID: Int) : String {
        val messageArray = context.resources.getStringArray(messageArrayID)
        var widgetText = messageArray.random()
        val lastWidgetText = LastWidgetTextFile(context).getLastText()

        if(messageArray.size > 1)  {
            while(widgetText == lastWidgetText) {
                widgetText = context.resources.getStringArray(messageArrayID).random()
            }
        }

        return widgetText
    }

}