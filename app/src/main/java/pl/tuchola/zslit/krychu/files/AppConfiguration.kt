package pl.tuchola.zslit.krychu.files
import android.content.Context
import androidx.preference.PreferenceManager
import pl.tuchola.zslit.krychu.weather.WeatherLocation

class AppConfiguration(private val context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var weatherLocation: String
    get() {
        val loc = preferences.getString("weatherLocation", WeatherLocation.DEFAULT_LOCATION)
        return loc ?: WeatherLocation.DEFAULT_LOCATION
    }
    set(value) {
        preferences.edit().putString("weatherLocation", value).apply()
    }

    var enablePatternDebianifying: Boolean
    get() {
        return preferences.getBoolean("enablePatternDebianifying", true)
    }
    set(value) {
        preferences.edit().putBoolean("enablePatternDebianifying", value).apply()
    }

}