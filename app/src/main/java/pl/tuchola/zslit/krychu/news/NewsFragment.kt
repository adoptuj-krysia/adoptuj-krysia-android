package pl.tuchola.zslit.krychu.news
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import kotlinx.android.synthetic.main.fragment_news.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.Boast
import pl.tuchola.zslit.krychu.common.NetworkDataProvider
import pl.tuchola.zslit.krychu.common.NetworkError
import pl.tuchola.zslit.krychu.files.AppConfiguration
import pl.tuchola.zslit.krychu.news.ZslitConnectionError.*
import pl.tuchola.zslit.krychu.news.debianifier.DebianifierPatternCollection
import pl.tuchola.zslit.krychu.news.debianifier.DebianifierXmlProvider

class NewsFragment : Fragment() {

    private var debianification: DebianifierPatternCollection? = null
    private var lastCreatedScraper: ZslitWebsiteScraper? = null

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null)
            return

        newsFragment_refresher.setOnRefreshListener {
            lastCreatedScraper?.cancelFetching()
            firstEntryLoading_progressBar.visibility = View.VISIBLE
            getNewsAndShowToUser()
            newsFragment_refresher.isRefreshing = false
        }

        getNewsAndShowToUser()
    }

    private fun getNewsAndShowToUser() {
        if(AppConfiguration(context!!).enablePatternDebianifying) {
            fetchDebianifierAndIntialize()
        } else {
            debianification = DebianifierPatternCollection(arrayOf())
            initializeNews()
        }
    }

    private fun fetchDebianifierAndIntialize() {
        val onSuccess = fun(col: DebianifierPatternCollection) {
            activity!!.runOnUiThread {
                debianification = col
                initializeNews()
            }
        }
        val onError = fun(err: NetworkError) {
            activity!!.runOnUiThread {
                if(err != NetworkError.NO_INTERNET_CONNECTION)
                    Boast.showLongMessage(getString(R.string.news_debianifier_xml_error), context!!)
                debianification = DebianifierPatternCollection(arrayOf())
                initializeNews()
            }
        }
        val provider = DebianifierXmlProvider()
        provider.setOnErrorListener(onError)
        provider.setOnSuccessListener(onSuccess)
        provider.startFetching()
    }

    private fun initializeNews() {
        super.onStart()
        if (news_recyclerView == null || context == null) return

        val newsy = mutableListOf<News>()
        this.lastCreatedScraper = ZslitWebsiteScraper()
        news_recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NewsViewAdapter(news_recyclerView, newsy, activity!!)
        news_recyclerView.adapter = adapter

        val onSuccess = fun(news: News) {
            if(news_recyclerView == null) return
            activity!!.runOnUiThread {
                firstEntryLoading_progressBar.visibility = View.INVISIBLE
                debianification?.debianifyNews(news)
                newsy.add(news)
                adapter.notifyDataSetChanged()
                adapter.setLoaded()
            }
        }

        val onError = fun(err: ZslitConnectionError) {
            activity!!.runOnUiThread {
                firstEntryLoading_progressBar.visibility = View.INVISIBLE
                when (err) {
                    INTERNET_ERROR -> Boast.showLongMessage(getString(R.string.news_error_internet), context!!)
                    INVALID_SERVER_RESPONSE -> Boast.showLongMessage(getString(R.string.news_error_zslit), context!!)
                    UNRECOGNIZED_ERROR -> Boast.showLongMessage(getString(R.string.news_error_unrecognized), context!!)
                    NO_NEWS_AVAILABLE -> {}
                }
                adapter.setCannotLoadMore()
                adapter.setOnLoadMoreListener(null)
                adapter.setLoaded();
            }
        }

        this.lastCreatedScraper!!.setOnSuccessListener(onSuccess)
        this.lastCreatedScraper!!.setOnErrorListener(onError)

        adapter.setOnLoadMoreListener(object : OnLoadMoreListener { override fun onLoadMore() {
            lastCreatedScraper!!.startFetching()
        }})

    }

}