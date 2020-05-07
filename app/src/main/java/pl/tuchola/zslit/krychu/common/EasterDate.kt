package pl.tuchola.zslit.krychu.common
import java.util.*

class EasterDate(private val year: Int = Calendar.getInstance().get(Calendar.YEAR)) {

    fun getEasterSundayDate(): Date {
        var month: Int
        var day: Int

        val a = year % 19
        val b = year / 100
        val c = year % 100
        val d = b / 4
        val e = b % 4
        val g = (8 * b + 13) / 25
        val h = (19 * a + b - d - g + 15) % 30
        val j = c / 4
        val k = c % 4
        val m = (a + 11 * h) / 319
        val r = (2 * e + 2 * j - k - h + m + 32) % 7

        month = (h - m + r + 90) / 25
        day = (h - m + r + month + 19) % 32

        val calendar = Calendar.getInstance()
        calendar.set(year, month - 1, day)
        return calendar.time
    }

}