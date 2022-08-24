package it.marvel.network.entities

import android.os.Parcelable
import androidx.room.Entity
import com.squareup.moshi.JsonClass
import kotlinx.parcelize.Parcelize

@Parcelize
@JsonClass(generateAdapter = true)
@Entity(tableName = "summary")
data class Summary(
    val resourceURI: String?,
    val name: String?
) : Parcelable