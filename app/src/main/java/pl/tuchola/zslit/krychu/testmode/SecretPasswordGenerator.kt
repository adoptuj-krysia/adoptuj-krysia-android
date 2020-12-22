package pl.tuchola.zslit.krychu.testmode
import pl.tuchola.zslit.krychu.common.md5

class SecretPasswordGenerator {

    fun generateSecretPassword(userId: String): String {
        val result = StringBuilder()
        result.append(userId)
        result.reverse()
        result.insert(0, "secret_");


        return result.toString().md5().reversed()
    }

}