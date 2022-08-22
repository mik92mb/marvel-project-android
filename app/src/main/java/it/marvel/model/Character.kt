package it.marvel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int?,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    val thumbnail: Image?,
    val urls: List<Url>? = emptyList(),
    val comics: Comic?,
    val stories: Comic?,
    val events: Comic?,
    val series: Comic?
) : Parcelable {

    fun imageUrl(): String? =
        thumbnail?.let {
            val safePath = it.path ?: return@let null
            val safeExtensions = it.extension ?: return@let null
            return@let "$safePath.$safeExtensions"
        }
}
