package pl.tuchola.zslit.krychu.news.debianifier
import android.accounts.NetworkErrorException
import android.os.AsyncTask
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import pl.tuchola.zslit.krychu.common.NetworkDataProvider
import pl.tuchola.zslit.krychu.common.NetworkError
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException
import javax.xml.parsers.SAXParserFactory

class DebianifierXmlProvider : NetworkDataProvider<DebianifierPatternCollection, NetworkError> {

    private val url = "https://adoptuj-krysia.github.io/xml/debianifier.xml"

    override fun startFetching(onSuccess: (DebianifierPatternCollection) -> Unit, onError: (NetworkError) -> Unit) {
        AsyncTask.execute {
            try {
                val client = OkHttpClient()
                client.setConnectTimeout(8, TimeUnit.SECONDS)
                client.setReadTimeout(8, TimeUnit.SECONDS)
                client.setWriteTimeout(8, TimeUnit.SECONDS)

                val request = Request.Builder().url(this.url).build()
                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(request: Request?, e: IOException?) {
                        if(e is UnknownHostException || e is NetworkErrorException)
                            onError(NetworkError.NO_INTERNET_CONNECTION)
                        else
                            onError(NetworkError.UNRECOGNIZED_ERROR)
                    }

                    override fun onResponse(response: Response?) {
                        if(response?.body() != null) {
                            try {
                                val factory = SAXParserFactory.newInstance()
                                val saxParser = factory.newSAXParser()
                                val handler = DebianifierSaxHandler()
                                saxParser.parse(response.body().byteStream(), handler)
                                onSuccess(handler.getParsedCollection())
                            } catch(e: Exception) {
                                onError(NetworkError.SERVER_INVALID_RESPONSE)
                            }
                        } else {
                            onError(NetworkError.SERVER_INVALID_RESPONSE)
                        }
                    }
                })
            }
            catch(e: TimeoutException) {
                onError(NetworkError.CONNECTION_TIMEOUT)
            }
            catch(e: Exception) {
                onError(NetworkError.UNRECOGNIZED_ERROR)
            }
        }
    }
    
}