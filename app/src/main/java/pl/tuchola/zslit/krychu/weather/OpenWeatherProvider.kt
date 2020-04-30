package pl.tuchola.zslit.krychu.weather

import android.accounts.NetworkErrorException
import android.os.AsyncTask
import android.util.Log
import android.view.View
import com.beust.klaxon.*
import com.squareup.okhttp.Callback
import com.squareup.okhttp.OkHttpClient
import com.squareup.okhttp.Request
import com.squareup.okhttp.Response
import kotlinx.android.synthetic.main.fragment_weather.*
import pl.tuchola.zslit.krychu.utils.Boast
import pl.tuchola.zslit.krychu.utils.KelvinTemperature
import java.io.IOException
import java.lang.StringBuilder
import java.net.URL
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit
import java.util.concurrent.TimeoutException

class OpenWeatherProvider : WeatherNetworkProvider {

    companion object {
        private const val URL =
            "https://api.openweathermap.org/data/2.5/weather?q=Tuchola&APPID=c8cd7a1d3d7a8ad8d251c6973336608e&lang=pl"
    }

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

    override fun startFetchingWeather(onSuccess: (Weather) -> Unit, onFailure: (WeatherFetchingError) -> Unit) {
        AsyncTask.execute {
            try {
                val client = OkHttpClient()
                client.setConnectTimeout(8, TimeUnit.SECONDS)
                client.setReadTimeout(8, TimeUnit.SECONDS)
                client.setWriteTimeout(8, TimeUnit.SECONDS)

                val request = Request.Builder().url(URL).build()

                client.newCall(request).enqueue(object : Callback {
                    override fun onFailure(request: Request?, e: IOException?) {
                        if(e is UnknownHostException || e is NetworkErrorException)
                            onFailure(WeatherFetchingError.NO_INTERNET_CONNECTION)
                        else
                            onFailure(WeatherFetchingError.UNRECOGNIZED_ERROR)
                    }

                    override fun onResponse(response: Response?) {
                        if(response?.body() != null) {
                            val weather = jsonToWeather(response.body().string())
                            if(weather != null)
                                onSuccess(weather)
                            else
                                onFailure(WeatherFetchingError.SERVER_INVALID_RESPONSE)
                        } else {
                            onFailure(WeatherFetchingError.SERVER_INVALID_RESPONSE)
                        }
                    }
                })
            }
            catch(e: TimeoutException) {
                onFailure(WeatherFetchingError.CONNECTION_TIMEOUT)
            }
            catch(e: Exception) {
                onFailure(WeatherFetchingError.UNRECOGNIZED_ERROR)
            }
        }
    }
}