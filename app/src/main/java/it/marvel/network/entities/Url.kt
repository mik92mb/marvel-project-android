package it.marvel.network.entities

import android.os.Parcelable
import androidx.room.Entity
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "url")
data class Url(
    val type: String?,
    val url: String
) : Parcelable