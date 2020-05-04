package pl.tuchola.zslit.krychu.view
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.news.*
import pl.tuchola.zslit.krychu.utils.Boast
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
        if (news_recyclerView == null || context == null) return

        val onSuccess = fun(news: News) {
            activity!!.runOnUiThread {
                val debianified = NewsDebianifier(news).getDebianifiedNews()
                news_recyclerView.adapter = NewsViewAdapter(arrayOf(debianified), context!!)
                news_recyclerView.layoutManager = LinearLayoutManager(context)
            }
        }

        val onError = fun(err: ZslitConnectionError) {
            activity!!.runOnUiThread {
                Boast.showLongMessage(err.toString(), context!!)
            }
        }

        ZslitWebsiteScraper().resolveUrlToNews(URL("http://zslit-tuchola.pl/skarpetkowewyzwanie/"), onSuccess, onError)
        ZslitWebsiteScraper().getNextNews()
    }

}