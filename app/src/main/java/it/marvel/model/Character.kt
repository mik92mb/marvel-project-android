package it.marvel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Character(
    val id: Int?,
    val name: String?,
    val description: String?,
    //   val modified: LocalDateTime?,
    val resourceURI: String?,
    val thumbnail: Image?,
    val urls: List<Url>? = emptyList(),
    /* val comics: List<Comic>? = emptyList(),
     val stories: List<Comic>? = emptyList(),
     val events: List<Comic>? = emptyList(),
     val series: List<Comic>? = emptyList()

     */
) : Parcelable {

    fun imageUrl(): String? =
        thumbnail?.let {
            val safePath = it.path ?: return@let null
            val safeExtensions = it.extension ?: return@let null
            return@let "$safePath.$safeExtensions"
        }
}