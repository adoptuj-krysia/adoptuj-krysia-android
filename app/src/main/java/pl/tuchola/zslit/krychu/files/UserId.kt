package pl.tuchola.zslit.krychu.files
import android.content.Context
import androidx.preference.PreferenceManager
import pl.tuchola.zslit.krychu.testmode.UserIdGenerator
import pl.tuchola.zslit.krychu.weather.WeatherLocation

class UserId(private val context: Context) {

    private val preferences = PreferenceManager.getDefaultSharedPreferences(context)

    var currentUid: String
        get() {
            val uid = preferences.getString("uniqueUserId", null)
            return if(uid == null) {
                val generatedUid = UserIdGenerator().generateUserId()
                currentUid = generatedUid
                generatedUid
            } else {
                uid
            }
        }
        set(value) {
            preferences.edit().putString("uniqueUserId", value).apply()
        }

}