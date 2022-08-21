package it.marvel.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Summary(
    val resourceURI: String?,
    val name: String?
) : Parcelable