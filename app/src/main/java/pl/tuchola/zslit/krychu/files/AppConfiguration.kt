package pl.tuchola.zslit.krychu.files
import android.content.Context
import androidx.preference.PreferenceManager
import pl.tuchola.zslit.krychu.weather.WeatherLocation

class AppConfiguration(private val context: Context) {

    var weatherLocation: String
    get() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        val loc = preferences.getString("weatherLocation", WeatherLocation.LOCATION_GPS)
        return loc ?: WeatherLocation.LOCATION_GPS
    }
    set(value) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putString("weatherLocation", value).apply()
    }

    var enablePatternDebianifying: Boolean
    get() {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        return preferences.getBoolean("enablePatternDebianifying", true)
    }
    set(value) {
        val preferences = PreferenceManager.getDefaultSharedPreferences(context)
        preferences.edit().putBoolean("enablePatternDebianifying", value).apply()
    }

}