package pl.tuchola.zslit.krychu.weather
import kotlin.random.Random

class FishingOpportunity(private val weather: Weather) {

    fun isWeatherGoodForFishing() : Boolean {
        val sum = weather.feelsLikeTemperatureCelcius
        + weather.temperatureCelcius
        + weather.windSpeedKmph

        return Random(sum.toInt()).nextBoolean()
    }

}