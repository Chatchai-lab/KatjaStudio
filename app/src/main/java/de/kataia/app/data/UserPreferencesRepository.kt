package de.kataia.app.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import java.io.IOException

// Namen definieren des DataStores
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_prefs")

class UserPreferencesRepository(private val context: Context) {

    //Definieren der Keys
    private companion object {
        val USER_XP = intPreferencesKey("user_xp")
        val USER_LEVEL = intPreferencesKey("user_level")
        val STREAK_COUNT = intPreferencesKey("streak_count")
        val LAST_COMPLETION_TIME = longPreferencesKey("last_completion_time")
    }

    //Lesen der Daten als Flow (Echtzeit-Updates für die UI)

    val userXp: Flow<Int> = context.dataStore.data
    .catch{ exception ->
        if (exception is IOException) emit(emptyPreferences()) else throw exception
    }
    .map{preferences -> preferences[USER_XP] ?: 0 } //Default: 0XP

    val streakCount: Flow<Int> = context.dataStore.data
        .map{preferences -> preferences[STREAK_COUNT] ?: 0 }

    //Schreiben der Daten (suspend fucntions)

    suspend fun updateXp(amount: Int) {
        context.dataStore.edit { preferences ->
            val currentXp = preferences[USER_XP] ?: 0
            preferences[USER_XP] = currentXp + amount
        }
    }

    suspend fun updateStreak(count: Int) {
        context.dataStore.edit { preferences ->
            preferences[STREAK_COUNT] = count
        }
    }

    suspend fun saveLastCompletionTime(timestamp: Long) {
        context.dataStore.edit { preferences ->
            preferences[LAST_COMPLETION_TIME] = timestamp
        }
    }
}