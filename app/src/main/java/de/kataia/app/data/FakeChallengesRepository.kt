package de.kataia.app.data

import android.content.Context
import de.kataia.app.model.Challenge
import kotlinx.serialization.json.Json

class FakeChallengeRepository(private val context: Context) : ChallengeRepository {

    // Wir laden die Challenges nur einmal (lazy), wenn sie gebraucht werden
    private val challenges: List<Challenge> by lazy {
        loadChallengesFromAssets()
    }

    private fun loadChallengesFromAssets(): List<Challenge> {
        return try {
            // 1. Datei öffnen
            val jsonString = context.assets.open("challenges.json")
                .bufferedReader()
                .use { it.readText() }

            // 2. Text in Kotlin-Objekte umwandeln (Dank der Serialisierung!)
            Json { ignoreUnknownKeys = true }.decodeFromString<List<Challenge>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList() // Falls ein Fehler auftritt, leere Liste zurückgeben
        }
    }

    override fun getAllChallenges(): List<Challenge> = challenges

    override fun getChallengeById(id: String): Challenge? {
        return challenges.find { it.id == id }
    }
}