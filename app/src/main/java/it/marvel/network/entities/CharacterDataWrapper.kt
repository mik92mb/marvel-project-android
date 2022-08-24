package it.marvel.network.entities

data class CharacterDataWrapper(
    val code: Int?,
    val status: String?,
    val copyright: String?,
    val attributionText: String?,
    val attributionHTML: String?,
    val etag: String?,
    val data: CharacterDataContainer?
)