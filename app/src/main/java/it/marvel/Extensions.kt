package it.marvel

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import org.threeten.bp.LocalDateTime
import org.threeten.bp.ZoneOffset
import java.math.BigInteger
import java.security.MessageDigest

fun getTimeStamp() = LocalDateTime.now().toEpochSecond(ZoneOffset.UTC).toString()

fun hashGenerator(): String {
    val stringToHash =
        "${getTimeStamp()}${Costants.PRIVATE_KEY}${Costants.PUBLIC_KEY}".toByteArray()
    val md = MessageDigest.getInstance("MD5")
    //  return BigInteger(1, md.digest(stringToHash)).toString()
    return BigInteger(1, md.digest(stringToHash)).toString(16).padStart(32, '0')
    // return md.digest(stringToHash).toString()
}

@Suppress("DEPRECATION")
fun Context.isNetworkAvailable(): Boolean {
    val connectivityManager =
        this.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager

    return if (Build.VERSION.SDK_INT >= 29) {
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

        capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
                || capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)
    } else {
        val activeNetworkInfo = connectivityManager.activeNetworkInfo
        activeNetworkInfo != null && activeNetworkInfo.isConnected
    }
}