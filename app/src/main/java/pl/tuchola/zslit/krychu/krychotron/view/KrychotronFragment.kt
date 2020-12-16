package pl.tuchola.zslit.krychu.krychotron.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_krychotron.*
import pl.tuchola.zslit.krychu.R


class KrychotronFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_krychotron, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val adapter = KrychotronViewPagerAdapter(childFragmentManager)
        adapter.addFragment(KrychotronSubfragment(), "Krychotron")
        adapter.addFragment(PelonixonSubfragment(), "Pelonixon")
        krychotronViewPager.adapter = adapter

        krychotronTabs.setupWithViewPager(krychotronViewPager)
        krychotronTabs.getTabAt(0)!!.setIcon(R.drawable.icon_krychotron)
        krychotronTabs.getTabAt(1)!!.setIcon(R.drawable.icon_pelonixon)
    }

}