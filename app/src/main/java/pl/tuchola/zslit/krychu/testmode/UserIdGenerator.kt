package pl.tuchola.zslit.krychu.testmode

class UserIdGenerator {

    companion object {
        private const val USER_ID_SIZE = 128
    }

    fun generateUserId(): String {
        val allowedChars = ('a'..'z') + ('0'..'9')
        return (1..USER_ID_SIZE).map { allowedChars.random() }.joinToString("")
    }

}