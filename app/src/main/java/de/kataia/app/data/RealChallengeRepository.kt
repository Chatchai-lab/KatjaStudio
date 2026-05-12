package de.kataia.app.data

import android.content.Context
import de.kataia.app.model.Challenge
import de.kataia.app.model.ChallengeCompletion
import kotlinx.coroutines.flow.Flow
import kotlinx.serialization.json.Json

class RealChallengeRepository(
    private val context: Context,
    private val completionDao: CompletionDao
) : ChallengeRepository {
    private val json = Json { ignoreUnknownKeys = true }

    private val challenges: List<Challenge> by lazy {
        try {
            context.assets.open("challenges.json")
                .bufferedReader()
                .use { it.readText() }
                .let { json.decodeFromString<List<Challenge>>(it) }
        } catch (e: Exception) {
            emptyList()
        }
    }

    override fun getAllChallenges(): List<Challenge> = challenges

    override fun getChallengeById(id: String) : Challenge? = challenges.find { it.id == id }

    override fun getDailyChallenge(): Challenge?{
        if (challenges.isEmpty()) return null
        val calendar = java.util.Calendar.getInstance()
        val dateSeed = (calendar.get(java.util.Calendar.YEAR) * 10000 +
                (calendar.get(java.util.Calendar.MONTH) + 1) * 100 +
                calendar.get(java.util.Calendar.DAY_OF_MONTH)).toLong()
        return challenges[kotlin.random.Random(dateSeed).nextInt(challenges.size)]
    }

    override suspend fun saveChallengeCompletion(completion: ChallengeCompletion) {
        // Speichert den Abschluss permanent in der Datenbank
        completionDao.insertCompletion(completion)
    }


    override fun getAllCompletions(): Flow<List<ChallengeCompletion>> {
        // Liefert alle Bilder für die Galerie
        return completionDao.getAllCompletions()
    }

    override fun getRecentCompletions(): Flow<List<ChallengeCompletion>> {
        //Liefert nur die neusten 5 für den Home-Screen
        return completionDao.getRecentCompletion()
    }
}
