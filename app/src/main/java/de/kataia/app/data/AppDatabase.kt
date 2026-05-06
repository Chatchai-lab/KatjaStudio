package de.kataia.app.data

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import de.kataia.app.model.Challenge
import de.kataia.app.model.ChallengeCompletion

@Database(
    entities = [ChallengeCompletion::class, Challenge::class],
    version = 1,
    exportSchema = false
)

@TypeConverters(Converters::class)
abstract class AppDatabase : RoomDatabase() {
    abstract fun completionDao(): CompletionDao
}