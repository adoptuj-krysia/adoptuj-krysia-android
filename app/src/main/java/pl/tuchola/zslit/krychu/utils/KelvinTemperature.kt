package pl.tuchola.zslit.krychu.utils

class KelvinTemperature(private val degrees: Double) {
    val celsius = degrees - 273.15
    val fahreneit = (degrees - 273.15) * 1.8 + 32.0
}
