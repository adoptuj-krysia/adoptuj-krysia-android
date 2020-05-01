package pl.tuchola.zslit.krychu.view
import android.os.Bundle
import android.text.method.LinkMovementMethod
import android.text.util.Linkify
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_information.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.io.ActivityLog
import pl.tuchola.zslit.krychu.BuildConfig;

class InformationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {
        ActivityLog(context!!).writeLine(getString(R.string.log_fragment_information))
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onStart() {
        super.onStart()
        appVersion_textView.text = getString(R.string.info_app_version)
            .replace("%s", BuildConfig.VERSION_NAME.toString(), true)

        projektWebisteLink_textView.paint.isUnderlineText = true
        projektWebisteLink_textView.movementMethod = LinkMovementMethod.getInstance()
    }

}