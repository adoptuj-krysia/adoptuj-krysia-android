package pl.tuchola.zslit.krychu.main

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_home.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.utils.Boast


class HomeFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_home, container, false)
    }

    override fun onStart() {
        super.onStart()
        friendPhoto_imageView.setOnClickListener {
            Boast.showLongMessage(getString(R.string.apk_friend_touched), this.context!!)
        }
    }
}