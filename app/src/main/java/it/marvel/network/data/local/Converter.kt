package it.marvel.network.data.local

import androidx.room.TypeConverter
import com.squareup.moshi.JsonAdapter
import com.squareup.moshi.Moshi
import com.squareup.moshi.Types
import it.marvel.network.entities.Summary
import it.marvel.network.entities.Url

class Converters {

    private val moshi = Moshi.Builder().build()

    private val urlsTypes = Types.newParameterizedType(List::class.java, Url::class.java)
    private val summariesTypes = Types.newParameterizedType(List::class.java, Summary::class.java)

    private val urlAdapter: JsonAdapter<List<Url>> = moshi.adapter(urlsTypes)
    private val summaryAdapter: JsonAdapter<List<Summary>> = moshi.adapter(summariesTypes)

    @TypeConverter
    fun urlsToJson(listCharacter: List<Url>?): String = urlAdapter.toJson(listCharacter)

    @TypeConverter
    fun jsonToUrls(jsonStr: String?) = jsonStr?.let { urlAdapter.fromJson(jsonStr) }

    @TypeConverter
    fun summarysToJson(Summarys: List<Summary>?): String = summaryAdapter.toJson(Summarys)

    @TypeConverter
    fun jsonToSummaries(jsonStr: String?) = jsonStr?.let { summaryAdapter.fromJson(jsonStr) }
}