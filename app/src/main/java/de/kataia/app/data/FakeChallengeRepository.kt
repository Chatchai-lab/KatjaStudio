package de.kataia.app.data

import android.content.Context
import de.kataia.app.model.Challenge
import de.kataia.app.model.ChallengeCompletion
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.emptyFlow
import kotlinx.serialization.json.Json

class FakeChallengeRepository(private val context: Context) : ChallengeRepository {

    companion object {
        private val json = Json { ignoreUnknownKeys = true }
    }

    // Wir laden die Challenges nur einmal (lazy), wenn sie gebraucht werden
    private val challenges: List<Challenge> by lazy {
        loadChallengesFromAssets()
    }

    private fun loadChallengesFromAssets(): List<Challenge> {
        return try {
            val jsonString = context.assets.open("challenges.json")
                .bufferedReader()
                .use { it.readText() }

            json.decodeFromString<List<Challenge>>(jsonString)
        } catch (e: Exception) {
            e.printStackTrace()
            emptyList()
        }
    }

    override fun getAllChallenges(): List<Challenge> = challenges

    override fun getChallengeById(id: String): Challenge? {
        return challenges.find { it.id == id }
    }

    override fun getDailyChallenge(): Challenge? {
        val all = getAllChallenges()
        if (all.isEmpty()) return null

        val calendar = java.util.Calendar.getInstance()
        val year = calendar.get(java.util.Calendar.YEAR)
        val month = calendar.get(java.util.Calendar.MONTH) + 1
        val day = calendar.get(java.util.Calendar.DAY_OF_MONTH)

        val dateSeed = (year * 10000 + month * 100 + day).toLong()
        val dailyIndex = kotlin.random.Random(dateSeed).nextInt(all.size)

        return all[dailyIndex]
    }

    override suspend fun saveChallengeCompletion(completion: ChallengeCompletion) {
        println("--- KATAIA STORAGE LOG ---")
        println("Challenge '${completion.challengeId}' wurde erfolgreich abgeschlossen!")
        println("Zeitpunkt: ${java.util.Date(completion.completedAt)}")
        println("Notiz von Katja: ${completion.note ?: "Keine Notiz"}")
        println("Bild gespeichert unter: ${completion.imagePath ?: "Kein Bild"}")
        println("--------------------------")
    }

    override fun getRecentCompletions(): Flow<List<ChallengeCompletion>> {
        return emptyFlow()
    }

    override fun getAllCompletions(): Flow<List<ChallengeCompletion>> {
        return emptyFlow()
    }

}