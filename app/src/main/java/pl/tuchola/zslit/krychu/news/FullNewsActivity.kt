package pl.tuchola.zslit.krychu.news
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.text.HtmlCompat
import com.bumptech.glide.Glide
import kotlinx.android.synthetic.main.activity_full_news.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.Boast


class FullNewsActivity : AppCompatActivity() {

    private var newsToShow : News? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_full_news)
        setSupportActionBar(news_toolbar)
        supportActionBar?.setDisplayHomeAsUpEnabled(true);
        supportActionBar?.setDisplayShowHomeEnabled(true);
    }

    override fun onStart() {
        super.onStart()

        newsToShow = intent.getSerializableExtra("NEWS_TO_SHOW") as News
        if(newsToShow != null && fullNewsBody_textView.text != null) {
            var body = newsToShow!!.htmlContent
            if(body.endsWith(".")) body = body.trimEnd('.')

            fullNewsBody_textView.text = HtmlCompat.fromHtml(body, 0)
            fullNewsHeader_textView.text = HtmlCompat.fromHtml( newsToShow!!.title, 0)

            if(newsToShow!!.imageLink != null) {
                @Suppress("DEPRECATION")
                Glide.with(this).load(newsToShow!!.imageLink).into(fullNewsImage_imageView);
            }
        }
    }

    override fun onSupportNavigateUp(): Boolean {
        onBackPressed()
        return true
    }
}