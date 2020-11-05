package pl.tuchola.zslit.krychu.krychotron
import android.content.Context
import android.media.MediaPlayer

class KrychotronEntryPlayer(private val krychotronEntry: KrychotronEntry, private val context: Context) {

    companion object {
        private var mediaPlayer: MediaPlayer = MediaPlayer()
    }

    fun play() {
        mediaPlayer.stop()
        mediaPlayer = MediaPlayer.create(context, krychotronEntry.soundResourceId)
        mediaPlayer.start()
    }

}