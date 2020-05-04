package pl.tuchola.zslit.krychu.news

import android.content.Context
import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.content.ContextCompat.startActivity
import androidx.recyclerview.widget.RecyclerView
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.view.FullNewsActivity


class NewsViewAdapter(private val news: Array<News>, private val context: Context)
    : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    companion object {
        private const val TYPE_HEADER = 0
        private const val TYPE_ITEM = 1
    }

    class NewsViewHolder(private val view: View) : RecyclerView.ViewHolder(view) {
        val newsContainer : LinearLayout = itemView.findViewById<LinearLayout>(R.id.news_linearLayout)
        val newsHeader: TextView = itemView.findViewById<TextView>(R.id.newsHeader_textView)
        val newsBody: TextView = itemView.findViewById<TextView>(R.id.newsBody_textView)
    }

    class HeaderViewHolder(private val view: View) : RecyclerView.ViewHolder(view)

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int) : RecyclerView.ViewHolder {
        return if(viewType == TYPE_HEADER) {
            val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.news_header, parent, false)
            HeaderViewHolder(itemView)
        } else {
            val itemView: View = LayoutInflater.from(parent.context).inflate(R.layout.news_item, parent, false)
            NewsViewHolder(itemView)
        }
    }

    override fun getItemCount() = news.size + 1

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if(holder is NewsViewHolder) {
            holder.newsHeader.text = news[position-1].header
            holder.newsBody.text = news[position-1].bodyShort
            holder.newsContainer.setOnClickListener {
                val intent = Intent(context, FullNewsActivity::class.java)
                intent.putExtra("NEWS_TO_SHOW", news[position-1])
                context.startActivity(intent)
            }
        }
    }

    override fun getItemViewType(position: Int): Int {
        return if (position == 0) TYPE_HEADER else TYPE_ITEM
    }

}