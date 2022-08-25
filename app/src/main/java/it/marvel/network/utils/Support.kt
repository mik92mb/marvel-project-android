package it.marvel.network.utils

import it.marvel.utils.Costants
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.math.BigInteger
import java.security.MessageDigest

object Support {

    fun getTimeStamp(): String = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString()

    fun hashGenerator(): String {
        val stringToHash =
            "${getTimeStamp()}${Costants.PRIVATE_KEY}${Costants.PUBLIC_KEY}".toByteArray()
        val md = MessageDigest.getInstance("MD5")
        return BigInteger(1, md.digest(stringToHash)).toString(16).padStart(32, '0')
    }
}