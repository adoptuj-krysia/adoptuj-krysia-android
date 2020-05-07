package pl.tuchola.zslit.krychu.news
import android.app.Activity
import android.content.Intent
import android.opengl.Visibility
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import androidx.core.text.HtmlCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import org.jsoup.Jsoup
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.files.AppConfiguration


class NewsViewAdapter(private val recyclerView: RecyclerView, private val news: List<News>, private val activity: Activity)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
        private const val TYPE_LOADING = 2
    }

    private var onLoadMoreListener: OnLoadMoreListener? = null
    private var isLoading = false
    private var visibleThreshold = 5
    private var lastVisibleItem = 0
    private var totalItemCount= 0
    private var canLoadMore = true
    private var lastLoadingHolder: LoadingViewHolder? = null
    private val linearLayoutManager = recyclerView.layoutManager as LinearLayoutManager

    init {
        recyclerView.addOnScrollListener(object : RecyclerView.OnScrollListener() {
            override fun onScrolled(recyclerView: RecyclerView, dx: Int, dy: Int) {
                super.onScrolled(recyclerView, dx, dy)
                totalItemCount = linearLayoutManager.itemCount + 1
                lastVisibleItem = linearLayoutManager.findLastVisibleItemPosition()
                if (!isLoading && totalItemCount <= lastVisibleItem + visibleThreshold) {
                    if (onLoadMoreListener != null && canLoadMore) {
                        onLoadMoreListener!!.onLoadMore()
                    }
                    isLoading = true
                }
            }
        })
    }

    private inner class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val newsContainer : LinearLayout = itemView.findViewById(R.id.news_linearLayout)
        val newsHeader: TextView = itemView.findViewById(R.id.newsHeader_textView)
        val newsBody: TextView = itemView.findViewById(R.id.newsBody_textView)
    }

    private inner class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val warning : TextView = itemView.findViewById(R.id.newsHeaderWarning_textView)
    }

    private inner class LoadingViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val loadingContainer : LinearLayout = itemView.findViewById(R.id.newsLoading_linearLayout)
        val loadingProgressBar : ProgressBar = itemView.findViewById(R.id.newsLoading_progressBar)
    }

    fun setOnLoadMoreListener(mOnLoadMoreListener: OnLoadMoreListener?) {
        onLoadMoreListener = mOnLoadMoreListener
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.news_header, parent, false)
                HeaderViewHolder(itemView)
            }
            TYPE_ITEM -> {
                val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
                NewsViewHolder(itemView)
            }
            else -> {
                val itemView: View = LayoutInflater.from(activity).inflate(R.layout.news_loading, parent, false)
                lastLoadingHolder = LoadingViewHolder(itemView)
                lastLoadingHolder!!
            }
        }
    }

    override fun getItemCount() = news.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NewsViewHolder) {
            holder.newsHeader.text = HtmlCompat.fromHtml(news[position-1].header, 0)
            holder.newsBody.text = Jsoup.parse(news[position-1].bodyShort).text()
            holder.newsContainer.setOnClickListener {
                val intent = Intent(activity.applicationContext, FullNewsActivity::class.java)
                intent.putExtra("NEWS_TO_SHOW", news[position-1])
                activity.startActivity(intent)
            }
        } else if(holder is LoadingViewHolder) {
            holder.loadingProgressBar.isIndeterminate = true
        } else if(holder is HeaderViewHolder && !AppConfiguration(activity).enablePatternDebianifying) {
            holder.warning.visibility = View.VISIBLE
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when {
            position == 0 -> TYPE_HEADER
            news.getOrNull(position) == null -> TYPE_LOADING
            else -> TYPE_ITEM
        }
    }

    fun setLoaded() {
        isLoading = false
    }

    fun setCannotLoadMore() {
        if(canLoadMore) {
            canLoadMore = false
            lastLoadingHolder?.loadingContainer?.visibility = View.INVISIBLE
            lastLoadingHolder?.loadingContainer?.layoutParams?.height = 0
            lastLoadingHolder?.loadingContainer?.layoutParams?.width = 0
            notifyDataSetChanged()
        }
    }
}