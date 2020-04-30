package pl.tuchola.zslit.krychu.utils
import android.content.Context
import android.widget.Toast

object Boast {

    private var toast: Toast? = null;

    fun showLongMessage(message: String, context: Context) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_LONG)
        toast!!.show()
    }

    fun showShortMessage(message: String, context: Context) {
        toast?.cancel()
        toast = Toast.makeText(context, message, Toast.LENGTH_SHORT)
        toast!!.show()
    }

}