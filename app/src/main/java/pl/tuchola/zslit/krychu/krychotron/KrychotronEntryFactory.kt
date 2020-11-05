package pl.tuchola.zslit.krychu.krychotron
import pl.tuchola.zslit.krychu.R

class KrychotronEntryFactory {
    fun getEntries() : Array<KrychotronEntry> {
        return arrayOf (
            KrychotronEntry(R.raw.krychotron_0_1, "One są wyposażone w panel krosowy…"),
            KrychotronEntry(R.raw.krychotron_0_2, "Przełącznik sieciowy switch…"),
            KrychotronEntry(R.raw.krychotron_0_3, "Serwer…"),
            KrychotronEntry(R.raw.krychotron_0_4, "I specjalną konsolę KVM…"),
            KrychotronEntry(R.raw.krychotron_0_5, "Dodatkowo znajduje się drukarka laserowa…"),
            KrychotronEntry(R.raw.krychotron_0_6, "Router bezprzewodowy…"),
            KrychotronEntry(R.raw.krychotron_0_7, "To, co jest wymagane, na egzamin…")
        )
    }
}