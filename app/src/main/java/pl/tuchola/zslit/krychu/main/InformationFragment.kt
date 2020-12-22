package pl.tuchola.zslit.krychu.main
import android.app.AlertDialog
import android.content.ClipData
import android.content.Context
import android.content.DialogInterface
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_information.*
import pl.tuchola.zslit.krychu.BuildConfig
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.Boast
import pl.tuchola.zslit.krychu.files.ActivityLog
import pl.tuchola.zslit.krychu.files.UserId


class InformationFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, avedInstanceState: Bundle?): View? {
        ActivityLog(requireContext()).writeLine(getString(R.string.log_fragment_information))
        return inflater.inflate(R.layout.fragment_information, container, false)
    }

    override fun onStart() {
        super.onStart()
        appVersion_textView.text = getString(R.string.info_app_version, BuildConfig.VERSION_NAME)

        friendInfoPhoto_imageView.setOnClickListener {
            Boast.showLongMessage(getString(R.string.apk_friend_touched_info), requireContext())
        }

        revealUserId_button.setOnClickListener {
            val uid = UserId(requireContext()).currentUid
            val copyToClipboard = {
                val clipboard = requireContext().getSystemService(Context.CLIPBOARD_SERVICE) as android.content.ClipboardManager
                val clip = ClipData.newPlainText("Adoptuj Krysia UID", uid)
                clipboard.setPrimaryClip(clip)
                Boast.showLongMessage(requireContext().getString(R.string.info_copied_to_clipboard), requireContext())
            }

            val builder: AlertDialog.Builder = AlertDialog.Builder(requireContext())
            builder.setTitle(requireContext().getString(R.string.info_uid))
                .setMessage(uid)
                .setCancelable(true)
                .setPositiveButton(requireContext().getString(R.string.info_copy_to_clipboard)) { _, _ -> copyToClipboard()}
                .setNegativeButton("OK", null)
            val alert: AlertDialog = builder.create()
            alert.show()
        }
    }

}