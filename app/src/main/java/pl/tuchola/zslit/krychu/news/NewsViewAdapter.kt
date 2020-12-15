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
    }

    private inner class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val newsContainer : LinearLayout = itemView.findViewById(R.id.news_linearLayout)
        val newsHeader: TextView = itemView.findViewById(R.id.newsHeader_textView)
        val newsBody: TextView = itemView.findViewById(R.id.newsBody_textView)
    }

    private inner class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val warning : TextView = itemView.findViewById(R.id.newsHeaderWarning_textView)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return when (viewType) {
            TYPE_HEADER -> {
                val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.news_header, parent, false)
                HeaderViewHolder(itemView)
            }
            else -> {
                val itemView: View =
                    LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
                NewsViewHolder(itemView)
            }
        }
    }

    override fun getItemCount() = news.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NewsViewHolder) {
            holder.newsHeader.text = HtmlCompat.fromHtml(news[position-1].title, 0)
            holder.newsBody.text = Jsoup.parse(news[position-1].shortDescription).text()
            holder.newsContainer.setOnClickListener {
                val intent = Intent(activity.applicationContext, FullNewsActivity::class.java)
                intent.putExtra("NEWS_TO_SHOW", news[position-1])
                activity.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> TYPE_HEADER
            else -> TYPE_ITEM
        }
    }
}