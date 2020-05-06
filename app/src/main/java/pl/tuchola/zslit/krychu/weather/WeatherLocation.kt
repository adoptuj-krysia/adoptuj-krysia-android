package pl.tuchola.zslit.krychu.weather

import java.util.*

class WeatherLocation(var locationName: String = LOCATION_GPS) {

    companion object {
        const val LOCATION_GPS = "GPS"
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

