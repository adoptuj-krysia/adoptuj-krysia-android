package pl.tuchola.zslit.krychu.logic
import android.content.Context
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.utils.EasterDate
import java.util.*

class WidgetEntryFactory(private val context: Context) {

    private val morningImages = listOf(
        R.drawable.friend_morning_1, R.drawable.friend_morning_2, R.drawable.friend_morning_3,
        R.drawable.friend_morning_4, R.drawable.friend_morning_5, R.drawable.friend_morning_6, R.drawable.friend_morning_7
    )

    private val eveningImages = listOf(
        R.drawable.friend_evening_1, R.drawable.friend_evening_2, R.drawable.friend_evening_3,
        R.drawable.friend_evening_4, R.drawable.friend_evening_5, R.drawable.friend_evening_6, R.drawable.friend_evening_7
    )

    private val nightImages = listOf(
        R.drawable.friend_night_1, R.drawable.friend_night_2, R.drawable.friend_night_3,
        R.drawable.friend_night_4, R.drawable.friend_night_5, R.drawable.friend_night_6, R.drawable.friend_night_7
    )

    private val noonImages = listOf(
        R.drawable.friend_noon_1, R.drawable.friend_noon_2, R.drawable.friend_noon_3,
        R.drawable.friend_noon_4, R.drawable.friend_noon_5, R.drawable.friend_noon_6, R.drawable.friend_noon_7,
        R.drawable.friend_noon_8
    )

    private val papaImages = listOf(
        R.drawable.friend_papa
    )

    private val specialImages = listOf(
        R.drawable.friend_special
    )

    private val santaImages = listOf(
        R.drawable.friend_santa_1, R.drawable.friend_santa_2, R.drawable.friend_santa_3,
        R.drawable.friend_santa_4, R.drawable.friend_santa_5, R.drawable.friend_santa_6
    )

    private val easterImages = listOf(
        R.drawable.friend_easter_1, R.drawable.friend_easter_2, R.drawable.friend_easter_3,
        R.drawable.friend_easter_4
    )

    fun getWidgetEntry() : WidgetEntry {
        val calendar = Calendar.getInstance()
        val currentHour = calendar.get(Calendar.HOUR_OF_DAY)
        val currentMinute = calendar.get(Calendar.MINUTE)
        val currentDay = calendar.get(Calendar.DAY_OF_MONTH)
        val currentMonth = calendar.get(Calendar.MONTH)
        val currentYear = calendar.get(Calendar.YEAR)

        val easterCalendar = Calendar.getInstance()
        easterCalendar.time = EasterDate(currentYear).getEasterSundayDate()

        val images: List<Int>
        val strings : Int

        when {
            //ładuje moduł 2137
            (currentHour == 21 && currentMinute == 37) -> {
                images = papaImages
                strings = R.array.messages_2137
            }

            //ładuje moduł mistycznego fioletowego Sławka
            ((1..1000).random() == 1000) -> {
                images = specialImages
                strings = R.array.messages_special
            }

            //ładuje moduł świąt BN
            (currentDay in 24..26 && currentMonth == Calendar.DECEMBER) -> {
                images = santaImages
                strings = R.array.messages_christmas
            }

            //ładuje moduł świąt wielkanocnych
            (easterCalendar.get(Calendar.MONTH) == currentMonth &&
            easterCalendar.get(Calendar.DAY_OF_MONTH) == currentDay)  -> {
                images = easterImages
                strings = R.array.messages_easter
            }

            //ładuje moduł poranka
            (currentHour in 6..12) -> {
                images = morningImages
                strings = R.array.messages_morning
            }

            //ładuje moduł południa
            (currentHour in 13..17) -> {
                images = noonImages
                strings = R.array.messages_noon
            }

            //ładuje moduł wieczora
            (currentHour in 18..22) -> {
                images = eveningImages
                strings = R.array.messages_evening
            }

            //ładuje moduł nocy
            else -> {
                images = nightImages
                strings = R.array.messages_night
            }
        }

        return WidgetEntry(images.random(), RandomWidgetMessage(context).getWidgetMessage(strings))
    }

}