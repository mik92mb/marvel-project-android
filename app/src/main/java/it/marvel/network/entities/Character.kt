package it.marvel.network.entities

import android.os.Parcelable
import androidx.room.Embedded
import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "characters")
data class Character(
    @PrimaryKey
    val id: Int,
    val name: String?,
    val description: String?,
    val modified: String?,
    val resourceURI: String?,
    @Embedded(prefix = "thumbnail")
    val thumbnail: Image?,
    val urls: List<Url> = emptyList(),
    @Embedded(prefix = "comics")
    val comics: Content?,
    @Embedded(prefix = "stories")
    val stories: Content?,
    @Embedded(prefix = "events")
    val events: Content?,
    @Embedded(prefix = "series")
    val series: Content?,
    val isFavorite: Boolean = false
) : Parcelable {

    fun imageUrl(): String? =
        thumbnail?.let {
            val safePath = it.path ?: return@let null
            val safeExtensions = it.extension ?: return@let null
            return@let "$safePath.$safeExtensions"
        }
}
