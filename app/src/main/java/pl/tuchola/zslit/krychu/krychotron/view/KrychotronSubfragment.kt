package pl.tuchola.zslit.krychu.krychotron.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.subfragment_krychotron.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.krychotron.KrychotronEntryFactory
import pl.tuchola.zslit.krychu.krychotron.KrychotronType


class KrychotronSubfragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.subfragment_krychotron, container, false)
    }

    override fun onResume() {
        super.onResume()
        val entries = KrychotronEntryFactory().getEntries(KrychotronType.KRYCHOTRON)
        val adapter =
            KrychotronEntryArrayAdapter(
                requireContext(),
                entries,
                KrychotronType.KRYCHOTRON
            )
        soundPicker.adapter = adapter
    }

}