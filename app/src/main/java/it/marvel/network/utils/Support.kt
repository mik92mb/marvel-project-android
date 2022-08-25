package it.marvel.network.utils

import java.security.MessageDigest

object Support {

    const val PUBLIC_API_KEY = "33c318ead0ca5ba1638e529c3b92e72e"
    private const val PRIVATE_API_KEY = "de89579c3552cbf435601db1a9e6c88c7253ed1d"
    private const val ALGORITHM_TYPE = "MD5"
    private const val HASH_FORMAT = "%s%s%s"

    fun hashGenerator(timestamp: String): String {
        val stringToHash = HASH_FORMAT.format(timestamp, PRIVATE_API_KEY, PUBLIC_API_KEY)
        return MessageDigest
            .getInstance(ALGORITHM_TYPE)
            .digest(stringToHash.toByteArray())
            .joinToString("") { "%02x".format(it) }
    }
}