package it.marvel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Comic(
    val available: Int?,
    val returned: Int?,
    val collectionURI: String?,
    val items: List<Summary>? = emptyList(),
) : Parcelable
