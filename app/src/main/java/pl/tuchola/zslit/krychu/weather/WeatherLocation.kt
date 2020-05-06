package pl.tuchola.zslit.krychu.weather
import java.util.*

class WeatherLocation(var locationName: String = DEFAULT_LOCATION) {

    companion object {
        const val DEFAULT_LOCATION = "Plaskosz"
        const val LOCATION_GPS = "GPS"

        val allowedLocations = arrayOf(
            "Gostycyn", "Kęsowo", "Cekcyn", "Tuchola", "Plaskosz",
            "Lubiewo", "Śliwice", "Bysław", "Bysławek", "GPS"
        )
    }

    init {
        locationName = when (locationName.toLowerCase(Locale.getDefault())) {
            "gostycyn" -> "Gostycyn"
            "kęsowo" -> "Kęsowo"
            "cekcyn" -> "Cekcyn"
            "tuchola", "plaskosz" -> "Tuchola"
            "bysław, bysławek, lubiewo" -> "Lubiewo"
            "śliwice" -> "Śliwice"
            "gps" -> "GPS"
            else -> throw IllegalArgumentException("Unrecognized OpenWeather location name")
        }
    }

}

