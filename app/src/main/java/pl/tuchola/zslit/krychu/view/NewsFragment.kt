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


class NewsFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_news, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if (savedInstanceState != null)
            return
        initializeNews()
    }

    private fun initializeNews() {
        super.onStart()
        if (news_recyclerView == null || context == null) return

        val newsy = mutableListOf<News>()
        val scraper = ZslitWebsiteScraper()
        news_recyclerView.layoutManager = LinearLayoutManager(context)
        val adapter = NewsViewAdapter(news_recyclerView, newsy, activity!!)
        news_recyclerView.adapter = adapter

        val onSuccess = fun(news: News) {
            if(news_recyclerView == null) return
            activity!!.runOnUiThread {
                newsy.add(NewsDebianifier(news).getDebianifiedNews())
                adapter.notifyDataSetChanged();
                adapter.setLoaded();
            }
        }

        val onError = fun(err: ZslitConnectionError) {
            activity!!.runOnUiThread {
                Boast.showLongMessage(err.toString(), context!!)
                adapter.setCannotLoadMore()
                adapter.setOnLoadMoreListener(null)
                adapter.setLoaded();
            }
        }

        adapter.setOnLoadMoreListener(object : OnLoadMoreListener { override fun onLoadMore() {
            scraper.getNextNews(onSuccess, onError)
        }})

    }

}