package de.kataia.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

/**
 * Das zentrale Datenmodell für eine Zeichen-Übung.
 * @Entity macht es zur Datenbank-Tabelle, @Serializable erlaubt das Laden aus JSON.
 */
@Serializable
@Entity(tableName = "challenges")
data class Challenge(
    @PrimaryKey
    val id: String,
    val title: String,
    val description: String,
    val difficulty: ChallengeDifficulty,
    val category: ChallengeCategory,
    val xpReward: Int,
    val estimatedMinutes: Int
)

@Serializable
enum class ChallengeDifficulty {
    EASY, MEDIUM, HARD
}

@Serializable
enum class ChallengeCategory {
    SHAPES, ANATOMY, STILL_LIFE, CREATIVE
}