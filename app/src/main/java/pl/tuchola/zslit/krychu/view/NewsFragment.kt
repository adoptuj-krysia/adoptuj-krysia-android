package pl.tuchola.zslit.krychu.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import kotlinx.android.synthetic.main.fragment_news.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.news.News
import pl.tuchola.zslit.krychu.news.NewsViewAdapter
import java.net.URL

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null)
            return
    }

    override fun onStart() {
        super.onStart()
        if(news_recyclerView == null || context == null) return

        val news = arrayOf(
            News("Testowa wiadomość", "Sunt dolor veniam do officia esse. Ea voluptate ullamco ullamco duis adipisicing esse magna in anim. Consectetur laborum ut cillum sint anim. Anim mollit incididunt sint exercitation aliqua adipisicing nisi adipisicing qui officia quis. Pariatur laborum est commodo nisi dolor excepteur non occaecat aliquip ullamco labore magna. Sint cillum mollit est dolore laboris officia. Ad eiusmod anim irure ullamco aliqua velit aute et eiusmod mollit velit sunt Lorem.", URL("http://google.com")),
            News("Testowa wiadomość", "Sunt dolor veniam do officia esse. Ea voluptate ullamco ullamco duis adipisicing esse magna in anim. Consectetur laborum ut cillum sint anim. Anim mollit incididunt sint exercitation aliqua adipisicing nisi adipisicing qui officia quis. Pariatur laborum est commodo nisi dolor excepteur non occaecat aliquip ullamco labore magna. Sint cillum mollit est dolore laboris officia. Ad eiusmod anim irure ullamco aliqua velit aute et eiusmod mollit velit sunt Lorem.", URL("http://google.com")),
            News("Testowa wiadomość", "Sunt dolor veniam do officia esse. Ea voluptate ullamco ullamco duis adipisicing esse magna in anim. Consectetur laborum ut cillum sint anim. Anim mollit incididunt sint exercitation aliqua adipisicing nisi adipisicing qui officia quis. Pariatur laborum est commodo nisi dolor excepteur non occaecat aliquip ullamco labore magna. Sint cillum mollit est dolore laboris officia. Ad eiusmod anim irure ullamco aliqua velit aute et eiusmod mollit velit sunt Lorem.", URL("http://google.com")),
            News("Testowa wiadomość", "Sunt dolor veniam do officia esse. Ea voluptate ullamco ullamco duis adipisicing esse magna in anim. Consectetur laborum ut cillum sint anim. Anim mollit incididunt sint exercitation aliqua adipisicing nisi adipisicing qui officia quis. Pariatur laborum est commodo nisi dolor excepteur non occaecat aliquip ullamco labore magna. Sint cillum mollit est dolore laboris officia. Ad eiusmod anim irure ullamco aliqua velit aute et eiusmod mollit velit sunt Lorem.", URL("http://google.com")),
            News("Testowa wiadomość", "Sunt dolor veniam do officia esse. Ea voluptate ullamco ullamco duis adipisicing esse magna in anim. Consectetur laborum ut cillum sint anim. Anim mollit incididunt sint exercitation aliqua adipisicing nisi adipisicing qui officia quis. Pariatur laborum est commodo nisi dolor excepteur non occaecat aliquip ullamco labore magna. Sint cillum mollit est dolore laboris officia. Ad eiusmod anim irure ullamco aliqua velit aute et eiusmod mollit velit sunt Lorem.", URL("http://google.com"))
        )

        news_recyclerView.adapter = NewsViewAdapter(news, context!!)
        news_recyclerView.layoutManager = LinearLayoutManager(context)
    }

}