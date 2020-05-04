package pl.tuchola.zslit.krychu.view
import android.app.Activity
import android.os.Bundle
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_full_news.*
import kotlinx.android.synthetic.main.news_item.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.news.News
import pl.tuchola.zslit.krychu.utils.Boast

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
            var body = newsToShow!!.bodyLong
            if(body.endsWith(".")) body = body.trimEnd('.')

            fullNewsBody_textView.text = HtmlCompat.fromHtml(body, 0)
            fullNewsHeader_textView.text = HtmlCompat.fromHtml( newsToShow!!.header, 0)

            if(newsToShow!!.imageLink != null) {
                Glide.with(this).load(newsToShow!!.imageLink).into(fullNewsImage_imageView);
            }
        }
    }
}