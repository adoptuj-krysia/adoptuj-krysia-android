package pl.tuchola.zslit.krychu.news
import android.opengl.Visibility
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import kotlinx.android.synthetic.main.news_loading.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.Boast
import pl.tuchola.zslit.krychu.common.NetworkError
import java.net.URL
import javax.xml.parsers.SAXParserFactory

class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null)
            return
        initializeNews()

        newsFragment_refresher.setOnRefreshListener {
            newsFragment_refresher.isRefreshing = true
            initializeNews()
        }
    }

    private fun initializeNews() {
        super.onStart()
        if (news_recyclerView == null || context == null) return

        firstEntryLoading_progressBar.visibility = View.VISIBLE
        val newsProvider = NewsXmlProvider()
        newsProvider.setOnSuccessListener { newsList ->
            requireActivity().runOnUiThread()  {
                news_recyclerView.layoutManager = LinearLayoutManager(context)
                val adapter = NewsViewAdapter(news_recyclerView, newsList, requireActivity())
                news_recyclerView.adapter = adapter
                firstEntryLoading_progressBar.visibility = View.GONE
                newsFragment_refresher.isRefreshing = false
            }
        }

        newsProvider.setOnErrorListener { error ->
            requireActivity().runOnUiThread {
                if(error == NetworkError.NO_INTERNET_CONNECTION) {
                    Boast.showLongMessage(requireContext().getString(R.string.news_error_internet), requireContext())
                }
                else {
                    Boast.showLongMessage(requireContext().getString(R.string.news_error_unrecognized), requireContext())
                }
                firstEntryLoading_progressBar.visibility = View.GONE
                newsFragment_refresher.isRefreshing = false
            }
        }

        newsProvider.startFetching()
    }

}