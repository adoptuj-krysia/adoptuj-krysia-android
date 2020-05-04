package pl.tuchola.zslit.krychu.view
import android.app.Activity
import android.os.Bundle
import androidx.core.text.HtmlCompat
import kotlinx.android.synthetic.main.activity_full_news.*
import kotlinx.android.synthetic.main.news_item.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.news.News

class FullNewsActivity : Activity() {

    private var newsToShow : News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_news)
    }

    override fun onStart() {
        super.onStart()
        newsToShow = intent.getSerializableExtra("NEWS_TO_SHOW") as News
        if(newsToShow != null && fullNewsBody_textView.text != null) {
            fullNewsBody_textView.text = HtmlCompat.fromHtml(newsToShow!!.bodyLong, 0)
            fullNewsHeader_textView.text = HtmlCompat.fromHtml( newsToShow!!.header, 0)
        }
    }
}