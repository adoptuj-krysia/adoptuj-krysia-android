package pl.tuchola.zslit.krychu.weather

import android.accounts.NetworkErrorException
import android.os.AsyncTask
import com.beust.klaxon.JsonObject
import com.beust.klaxon.Parser
import com.beust.klaxon.lookup
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import pl.tuchola.zslit.krychu.common.KelvinTemperature
import pl.tuchola.zslit.krychu.common.NetworkDataProvider
import pl.tuchola.zslit.krychu.common.NetworkError
import java.io.IOException
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class OpenWeatherProvider(private val location: WeatherLocation) :
    NetworkDataProvider<Weather, NetworkError>() {

    private val url =
        "https://api.openweathermap.org/data/2.5/weather?q=${location.locationName}&APPID=c8cd7a1d3d7a8ad8d251c6973336608e&lang=pl"

    private var isCancelled = false

    private fun jsonToWeather(json: String) : Weather? {
        return try {
            val parser = Parser.default()
            val jsonObject = parser.parse(StringBuilder(json)) as JsonObject

            var weatherDescription = jsonObject.lookup<String>("weather.description")[0]
            var temperatureCelcius = jsonObject.lookup<Double>("main.temp")[0]
            var feelsLikeTemperatureCelcius = jsonObject.lookup<Double>("main.feels_like")[0]
            var windSpeedKmph = jsonObject.lookup<Double>("wind.speed")[0]

            temperatureCelcius = KelvinTemperature(temperatureCelcius).celsius
            feelsLikeTemperatureCelcius = KelvinTemperature(feelsLikeTemperatureCelcius).celsius

            Weather(windSpeedKmph, temperatureCelcius, feelsLikeTemperatureCelcius, weatherDescription)
        } catch(e: Exception) {
            null
        }
    }

    override fun cancelFetching() {
        isCancelled = true
    }

    override fun startFetching() {
        isCancelled = false
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
                            if(!isCancelled) onError?.invoke(NetworkError.NO_INTERNET_CONNECTION)
                        else
                            if(!isCancelled) onError?.invoke(NetworkError.UNRECOGNIZED_ERROR)
                    }

                    override fun onResponse(response: Response?) {
                        if(response?.body() != null) {
                            val weather = jsonToWeather(response.body().string())
                            if(weather != null)
                                if(!isCancelled) onSuccess?.invoke(weather)
                            else
                                if(!isCancelled) onError?.invoke(NetworkError.SERVER_INVALID_RESPONSE)
                        } else {
                            if(!isCancelled) onError?.invoke(NetworkError.SERVER_INVALID_RESPONSE)
                        }
                    }
                })
            }
            catch(e: TimeoutException) {
                if(!isCancelled) onError?.invoke(NetworkError.CONNECTION_TIMEOUT)
            }
            catch(e: Exception) {
                if(!isCancelled) onError?.invoke(NetworkError.UNRECOGNIZED_ERROR)
            }
        }
    }
}