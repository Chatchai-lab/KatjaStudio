package de.kataia.app.data

import androidx.room.*
import de.kataia.app.model.ChallengeCompletion
import de.kataia.app.model.ChallengeWithCompletion
import kotlinx.coroutines.flow.Flow

@Dao
interface CompletionDao {

    // 1. Speichert einen neuen Abschluss (Katja hat fertig gezeichnet!)
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCompletion(completion: ChallengeCompletion)

    // 2. Holt alle Abschlüsse für die Galerie
    @Query("SELECT * FROM challenge_completions ORDER BY completedAt DESC")
    fun getAllCompletions(): Flow<List<ChallengeCompletion>>

    // 3. Holt eine Challenge inklusive ihrem Bild/Notiz (für Details)
    @Transaction
    @Query("SELECT * FROM challenges WHERE id = :challengeId")
    fun getChallengeWithCompletion(challengeId: String): Flow<ChallengeWithCompletion?>

    // 4. Den allerletzten Abschluss holen (wichtig für die Streak-Berechnung)
    @Query("SELECT * FROM challenge_completions ORDER BY completedAt DESC LIMIT 1")
    suspend fun getLatestCompletion(): ChallengeCompletion?

    @Query("SELECT * FROM challenge_completions ORDER BY completedAt DESC LIMIT 5")
    fun getRecentCompletion(): Flow<List<ChallengeCompletion>>
}