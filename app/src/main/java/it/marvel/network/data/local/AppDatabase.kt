package it.marvel.network.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import it.marvel.network.entities.Character

@TypeConverters(Converters::class)
@Database(entities = [Character::class], version = 1, exportSchema = false)
abstract class AppDatabase : RoomDatabase() {

    abstract fun characterDao(): LocalDataSource
}