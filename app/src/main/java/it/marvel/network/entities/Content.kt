package it.marvel.network.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "content")
data class Content(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<Summary> = emptyList(),
) : Parcelable
