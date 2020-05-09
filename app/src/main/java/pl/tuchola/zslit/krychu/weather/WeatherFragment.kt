package pl.tuchola.zslit.krychu.weather
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import kotlinx.android.synthetic.main.fragment_weather.*
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.R.string
import pl.tuchola.zslit.krychu.common.Boast
import pl.tuchola.zslit.krychu.common.NetworkError
import pl.tuchola.zslit.krychu.files.AppConfiguration
import java.text.DecimalFormat


class WeatherFragment : Fragment() {

    private var weatherLocation: String = WeatherLocation.DEFAULT_LOCATION

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_weather, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        if(savedInstanceState != null)
            return

        weatherLocation = AppConfiguration(this.context!!).weatherLocation
        if(weatherLocation == WeatherLocation.LOCATION_GPS) {
            checkWeather_button.text = getString(R.string.weather_check_button_gps)
        } else {
            checkWeather_button.text = getString(R.string.weather_check_button, weatherLocation)
        }

        if(weatherLocation == "Kęsowo") {
            welcomeFriendWeatherMessage_textView.text = getString(R.string.apk_weather_welcome_kesowo)
        } else if(weatherLocation != "Plaskosz") {
            welcomeFriendWeatherMessage_textView.text = getString(R.string.apk_weather_welcome_sad, weatherLocation)
        }

        writeWeather(null)
        weatherPhoto_imageView.setOnClickListener {
            Boast.showLongMessage(getString(string.apk_friend_touched_weather), context!!)
        }

        checkWeather_button.setOnClickListener {
            weather_progressBar.visibility = View.VISIBLE
            checkWeather_button.isEnabled = false

            val onError = fun(error: NetworkError) {
                activity?.runOnUiThread {
                    if(weather_progressBar != null) {
                        if(error == NetworkError.SERVER_INVALID_RESPONSE)
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

            val provider = OpenWeatherProvider(WeatherLocation(weatherLocation))
            provider.setOnSuccessListener(onSuccess)
            provider.setOnErrorListener(onError)
            provider.startFetching()
        }
    }

    private fun writeWeather(weather: Weather?) {
        val strWind: String; val strFeelTemp: String;
        val strTemp: String; val strDescription: String
        if(weather == null) {
            val strUnrecognized = getString(R.string.weather_unrecognized)
            strWind = getString(R.string.weather_wind_speed, strUnrecognized)
            strTemp = getString(R.string.weather_temperature, strUnrecognized)
            strFeelTemp = getString(R.string.weather_feels_like, strUnrecognized)
            strDescription = getString(R.string.weather_description, strUnrecognized)
        } else {
            val df = DecimalFormat("0.00")
            strWind = getString(R.string.weather_wind_speed, df.format(weather.windSpeedKmph))
            strTemp = getString(R.string.weather_temperature, df.format(weather.temperatureCelcius))
            strFeelTemp =  getString(R.string.weather_feels_like, df.format(weather.feelsLikeTemperatureCelcius))
            strDescription = getString(R.string.weather_description, weather.weatherDescription)
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