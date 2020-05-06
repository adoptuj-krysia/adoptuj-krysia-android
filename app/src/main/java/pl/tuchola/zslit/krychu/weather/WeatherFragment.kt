package pl.tuchola.zslit.krychu.weather
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.R.string
import pl.tuchola.zslit.krychu.files.AppConfiguration
import pl.tuchola.zslit.krychu.utils.Boast
import java.text.DecimalFormat


class WeatherFragment : Fragment() {

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        if(savedInstanceState != null)
            return

        Boast.showLongMessage(AppConfiguration(this.context!!).weatherLocation, context!!)

        writeWeather(null)
        weatherPhoto_imageView.setOnClickListener {
            Boast.showLongMessage(getString(string.apk_friend_touched_weather), context!!)
        }

        checkWeather_button.setOnClickListener {
            weather_progressBar.visibility = View.VISIBLE
            checkWeather_button.isEnabled = false

            val onError = fun(error: WeatherFetchingError) {
                activity?.runOnUiThread {
                    if(weather_progressBar != null) {
                        if(error == WeatherFetchingError.SERVER_INVALID_RESPONSE)
                            Boast.showLongMessage(getString(R.string.error_weather_api), context!!)
                        else
                            Boast.showLongMessage(getString(R.string.error_internet_connection), context!!)
                        weather_progressBar.visibility = View.INVISIBLE
                        checkWeather_button.isEnabled = true
                    }
                }
            }

            val onSuccess = fun(weather: Weather) {
                activity?.runOnUiThread {
                    if(weather_progressBar != null) {
                        writeWeather(weather)
                        weather_progressBar.visibility = View.INVISIBLE
                        checkWeather_button.isEnabled = true
                    }
                }
            }

            OpenWeatherProvider(WeatherLocation()).startFetchingWeather(onSuccess, onError)
        }
    }

    private fun writeWeather(weather: Weather?) {
        var strWind = getString(R.string.weather_wind_speed)
        var strTemp = getString(R.string.weather_temperature)
        var strFeelTemp = getString(R.string.weather_feels_like)
        var strDescription = getString(R.string.weather_description)

        if(weather == null) {
            val strUnrecognized = getString(R.string.weather_unrecognized)
            strWind = strWind.replace("%s", strUnrecognized, true)
            strTemp = strTemp.replace("%s", strUnrecognized, true)
            strFeelTemp = strFeelTemp.replace("%s", strUnrecognized, true)
            strDescription = strDescription.replace("%s", strUnrecognized, true)
        } else {
            val df = DecimalFormat("0.00")
            strWind = strWind.replace("%s", df.format(weather.windSpeedKmph), true)
            strTemp = strTemp.replace("%s", df.format(weather.temperatureCelcius), true)
            strFeelTemp = strFeelTemp.replace("%s", df.format(weather.feelsLikeTemperatureCelcius), true)
            strDescription = strDescription.replace("%s", weather.weatherDescription, true)
        }

        if(weatherDescription_textView != null) {
            weatherWindSpeed_textView.text = strWind
            weatherTemperature_textView.text = strTemp
            weatherFeelsLike_textView.text = strFeelTemp
            weatherDescription_textView.text = strDescription
        }

        if(weather == null) {
            weatherFishing_textView.text = getString(R.string.fishing_opportunity_unknown)
        }
        else {
            when(FishingOpportunityCalculator(weather).getFishingOpportunity()) {
                FishingOpportunity.BAD_TEMPERATURE ->
                    weatherFishing_textView.text = getString(R.string.fishing_opportunity_temperature)
                FishingOpportunity.BAD_RAIN ->
                    weatherFishing_textView.text = getString(R.string.fishing_opportunity_rain)
                else ->
                    weatherFishing_textView.text = getString(R.string.fishing_opportunity_good)
            }
        }
    }

}