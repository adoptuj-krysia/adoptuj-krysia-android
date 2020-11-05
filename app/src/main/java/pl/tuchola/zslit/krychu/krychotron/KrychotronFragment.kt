package pl.tuchola.zslit.krychu.krychotron
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_krychotron.*
import pl.tuchola.zslit.krychu.R


class KrychotronFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_krychotron, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val entries = KrychotronEntryFactory().getEntries()
        val adapter = KrychotronEntryArrayAdapter(requireContext(), entries)
        soundPicker.adapter = adapter
    }

}