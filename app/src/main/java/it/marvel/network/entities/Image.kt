package it.marvel.network.entities

import android.os.Parcelable
import androidx.room.Entity
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity(tableName = "image")
data class Image(
    val path: String?,
    val extension: String?
) : Parcelable
