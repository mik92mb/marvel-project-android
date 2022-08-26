package it.marvel.network.data.local

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import it.marvel.network.entities.Character

@Dao
interface LocalDataSource {

    @Query("SELECT * FROM characters")
    suspend fun getCharacters(): List<Character>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCharacters(characters: List<Character>)

    @Query("SELECT * FROM characters WHERE isFavorite == 1")
    fun getFavoriteCharacters(): LiveData<List<Character>>

    @Query("UPDATE characters SET isFavorite = :isFavorite WHERE id =:characterId")
    suspend fun updateCharacters(isFavorite: Boolean, characterId: Int)

}