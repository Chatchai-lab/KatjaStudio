package de.kataia.app.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName = "challenge_completions")
data class ChallengeCompletion(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val challengeId: String,
    val completedAt: Long,
    val durationMinutes: Int,
    val note: String? = null,
    val imagePath: String? = null
)
