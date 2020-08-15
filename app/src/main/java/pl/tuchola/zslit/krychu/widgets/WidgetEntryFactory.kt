package pl.tuchola.zslit.krychu.widgets
import android.content.Context
import pl.tuchola.zslit.krychu.R
import pl.tuchola.zslit.krychu.common.EasterDate
import java.util.*

class WidgetEntryFactory(private val context: Context) {

    private val morningImages = listOf(
        R.drawable.friend_morning_1, R.drawable.friend_morning_2, R.drawable.friend_morning_3,
        R.drawable.friend_morning_4, R.drawable.friend_morning_5, R.drawable.friend_morning_6,
        R.drawable.friend_morning_7, R.drawable.friend_morning_8, R.drawable.friend_morning_9,
        R.drawable.friend_morning_10
    )

    private val eveningImages = listOf(
        R.drawable.friend_evening_1, R.drawable.friend_evening_2, R.drawable.friend_evening_3,
        R.drawable.friend_evening_4, R.drawable.friend_evening_5, R.drawable.friend_evening_6,
        R.drawable.friend_evening_7, R.drawable.friend_evening_8, R.drawable.friend_evening_9,
        R.drawable.friend_evening_10
    )

    private val nightImages = listOf(
        R.drawable.friend_night_1, R.drawable.friend_night_2, R.drawable.friend_night_3,
        R.drawable.friend_night_4, R.drawable.friend_night_5, R.drawable.friend_night_6,
        R.drawable.friend_night_7, R.drawable.friend_night_8, R.drawable.friend_night_9,
        R.drawable.friend_night_10
    )

    private val noonImages = listOf(
        R.drawable.friend_noon_1, R.drawable.friend_noon_2, R.drawable.friend_noon_3,
        R.drawable.friend_noon_4, R.drawable.friend_noon_5, R.drawable.friend_noon_6,
        R.drawable.friend_noon_7, R.drawable.friend_noon_8
    )

    private val ganjaImages = listOf(
        R.drawable.friend_420_1, R.drawable.friend_420_2
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

    private val partyImages = listOf(
        R.drawable.friend_party_1, R.drawable.friend_party_2, R.drawable.friend_party_3,
        R.drawable.friend_party_4, R.drawable.friend_party_5, R.drawable.friend_party_6
    )

    private val polandImages = listOf(
        R.drawable.friend_poland_1, R.drawable.friend_poland_2, R.drawable.friend_poland_3,
        R.drawable.friend_poland_4
    )

    private val childImages = listOf(
        R.drawable.friend_children_1, R.drawable.friend_children_2, R.drawable.friend_children_3,
        R.drawable.friend_children_4
    )

    private val halloweenImages = listOf(
        R.drawable.friend_halloween_1, R.drawable.friend_halloween_2, R.drawable.friend_halloween_3,
        R.drawable.friend_halloween_4, R.drawable.friend_halloween_5
    )

    private val fatherImages = listOf(
        R.drawable.friend_father_1, R.drawable.friend_father_2, R.drawable.friend_father_3,
        R.drawable.friend_father_4
    )

    private val motherImages = listOf(
        R.drawable.friend_mother_1, R.drawable.friend_mother_2, R.drawable.friend_mother_3,
        R.drawable.friend_mother_4
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
            //ładuje moduł mitycznego fioletowego Sławka
            ((1..1000).random() == 1000) -> {
                images = specialImages
                strings = R.array.messages_special
            }

            //ładuje moduł 2137
            (currentHour == 21 && currentMinute == 37) -> {
                images = papaImages
                strings = R.array.messages_2137
            }

            //ładuje moduł 420
            (currentHour == 4 && currentMinute in 20..25) -> {
                images = ganjaImages
                strings = R.array.messages_420
            }

            //ładuje moduł na dzień ojca
            (currentDay == 23 && currentMonth == Calendar.JUNE) -> {
                images = fatherImages
                strings = R.array.messages_fathers_day
            }

            //ładuje moduł na dzień matki
            (currentDay == 26 && currentMonth == Calendar.MAY) -> {
                images = motherImages
                strings = R.array.messages_mothers_day
            }

            //ładuje moduł mikołajkowy
            (currentDay == 6 && currentMonth == Calendar.DECEMBER) -> {
                images = santaImages
                strings = R.array.messages_6th_december
            }

            //ładuje moduł świąt BN
            (currentDay in 24..26 && currentMonth == Calendar.DECEMBER) -> {
                images = santaImages
                strings = R.array.messages_christmas
            }

            //ładuje moduł halloweenowy
            (currentDay == 31 && currentMonth == Calendar.OCTOBER) -> {
                images = halloweenImages
                strings = R.array.messages_halloween
            }

            //ładuje moduł świąt wielkanocnych
            (easterCalendar.get(Calendar.MONTH) == currentMonth &&
            easterCalendar.get(Calendar.DAY_OF_MONTH) == currentDay)  -> {
                images = easterImages
                strings = R.array.messages_easter
            }

            //ładuje moduł patriotyczny
            (currentDay == 11 && currentMonth == Calendar.NOVEMBER) ||
            (currentDay == 1 && currentMonth == Calendar.MAY) ||
            (currentDay == 3 && currentMonth == Calendar.MAY) ||
            (currentDay == 1 && currentMonth == Calendar.AUGUST) -> {
                images = polandImages
                strings = R.array.messages_polish_patriot
            }

            //ładuje modul sylwestrowy
            (currentDay == 31 && currentMonth == Calendar.DECEMBER) -> {
                images = partyImages
                strings = R.array.messages_new_years_eve
            }

            //ładuje moduł noworoczny
            (currentDay == 1 && currentMonth == Calendar.JANUARY) -> {
                images = partyImages
                strings = R.array.messages_new_year
            }

            //ładuje moduł dnia dziecka
            (currentDay == 1 && currentMonth == Calendar.JUNE) -> {
                images = childImages
                strings = R.array.messages_childrens_day
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