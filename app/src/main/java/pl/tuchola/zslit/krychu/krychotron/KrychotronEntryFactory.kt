package pl.tuchola.zslit.krychu.krychotron
import pl.tuchola.zslit.krychu.R
import java.util.Calendar

class KrychotronEntryFactory {
    fun getEntries() : Array<KrychotronEntry> {
        val cal = Calendar.getInstance()
        val hour = cal.get(Calendar.HOUR_OF_DAY)
        val minute = cal.get(Calendar.MINUTE)

        val entries = mutableListOf(
            KrychotronEntry(R.raw.krychotron_0_1, "One są wyposażone w panel krosowy…"),
            KrychotronEntry(R.raw.krychotron_0_2, "Przełącznik sieciowy switch…"),
            KrychotronEntry(R.raw.krychotron_0_3, "Serwer…"),
            KrychotronEntry(R.raw.krychotron_0_4, "I specjalną konsolę KVM…"),
            KrychotronEntry(R.raw.krychotron_0_5, "Dodatkowo znajduje się drukarka laserowa…"),
            KrychotronEntry(R.raw.krychotron_0_6, "Router bezprzewodowy…"),
            KrychotronEntry(R.raw.krychotron_0_7, "To, co jest wymagane, na egzamin…")
        )

        if(hour == 3 && minute in 30..35) {
            entries.add(0, KrychotronEntry(R.raw.krychotron_extra_1, "Biały Kryś [3:33 special]"))
        }

        return entries.toTypedArray()
    }
}