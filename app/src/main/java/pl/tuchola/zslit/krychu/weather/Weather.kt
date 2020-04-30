package pl.tuchola.zslit.krychu.weather

data class Weather (
    val windSpeedKmph: Double,
    val temperatureCelcius: Double,
    val feelsLikeTemperatureCelcius: Double,
    val weatherDescription: String
)