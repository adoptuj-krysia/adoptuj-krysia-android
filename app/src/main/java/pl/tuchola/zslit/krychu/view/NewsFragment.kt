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
import pl.tuchola.zslit.krychu.news.Debianifier
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

        val debianifier = Debianifier(
            "Testowa wiadomość",
            "Dolore do aute ipsum commodo mollit dolor. Cupidatat tempor non duis laboris id occaecat laborum mollit sunt est. Exercitation incididunt adipisicing ullamco tempor labore incididunt aliqua est mollit esse nisi. Ea id anim nisi esse reprehenderit reprehenderit sit nulla anim veniam. Aliqua sunt eiusmod dolor ut ullamco pariatur qui dolor laboris id nisi. Laborum amet ut fugiat excepteur culpa ipsum veniam commodo eu sint.Fugiat aliquip irure minim nulla tempor dolor excepteur aliquip. Ex sint excepteur Lorem ad dolor aute occaecat sint irure. In ea officia in ea deserunt et. Laborum quis commodo deserunt voluptate labore officia pariatur incididunt pariatur consequat.Pariatur ex ut aute duis. Est duis officia nulla tempor occaecat nostrud tempor reprehenderit ad proident labore amet. Sint aliqua velit sunt Lorem do dolor Lorem velit dolore aliquip commodo sit anim. Aliquip minim pariatur ipsum tempor do nulla minim commodo ipsum consectetur velit et ad mollit. Elit et laboris ipsum ut nostrud et magna et."
        )

        val news = arrayOf(
            News(0, debianifier.debianifyHeader(), debianifier.debianifyContent().split('.').first() + "...", debianifier.debianifyContent())
        )

        news_recyclerView.adapter = NewsViewAdapter(news, context!!)
        news_recyclerView.layoutManager = LinearLayoutManager(context)
    }

}