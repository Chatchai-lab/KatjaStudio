package de.kataia.app.data

import de.kataia.app.model.Challenge
import de.kataia.app.model.ChallengeCompletion
import kotlinx.coroutines.flow.Flow

/**
 * Schnittstelle für den Zugriff auf Challenges
 */
interface ChallengeRepository {
    fun getAllChallenges(): List<Challenge>
    fun getChallengeById(id: String): Challenge?
    fun getDailyChallenge(): Challenge?
    suspend fun saveChallengeCompletion(completion: ChallengeCompletion)
    fun getRecentCompletions(): Flow<List<ChallengeCompletion>>

    fun getAllCompletions(): Flow<List<ChallengeCompletion>>
    }
