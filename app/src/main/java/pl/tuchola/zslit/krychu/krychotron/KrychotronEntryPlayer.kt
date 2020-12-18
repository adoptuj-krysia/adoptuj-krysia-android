package pl.tuchola.zslit.krychu.krychotron
import android.content.Context
import android.media.MediaPlayer

class KrychotronEntryPlayer(private val krychotronEntry: KrychotronEntry, private val context: Context) {

    companion object {
        private var mediaPlayer: MediaPlayer? = MediaPlayer()
    }

    private fun stopAudio() {
        try {
            mediaPlayer!!.reset()
            mediaPlayer!!.stop()
            mediaPlayer!!.release()
            mediaPlayer = null
        }
        catch(e: Exception) {}
    }

    fun play() {
        stopAudio()

        mediaPlayer = MediaPlayer.create(context, krychotronEntry.soundResourceId)
        mediaPlayer!!.start()
        mediaPlayer!!.setOnCompletionListener{ stopAudio() }
    }

}