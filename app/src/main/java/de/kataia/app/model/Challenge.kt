package de.kataia.app.model

import kotlinx.serialization.Serializable

/**
 * Definiert die Schwierigkeitsgrade der Challenges
 */
@Serializable
enum class ChallengeDifficulty {
    EASY, MEDIUM, HARD
}

/**
 * Definiert die Kategorien der Übungen
 */
@Serializable
enum class ChallengeCategory {
    SHAPES, ANATOMY, STILL_LIFE, CREATIVE
}

/**
 * Das zentrale Datenmodell für eine Zeichen-Übung
 */
@Serializable
data class Challenge(
    val id: String,
    val title: String,
    val description: String,
    val difficulty: ChallengeDifficulty,
    val category: ChallengeCategory,
    val xpReward: Int,
    val estimatedMinutes: Int
)