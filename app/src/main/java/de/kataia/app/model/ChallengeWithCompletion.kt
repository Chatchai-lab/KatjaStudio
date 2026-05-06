package de.kataia.app.model

import androidx.room.Embedded
import androidx.room.Relation

data class ChallengeWithCompletion(
    @Embedded
    val challenge: Challenge, // Die Basis-Challenge (Titel, XP, etc.)

    @Relation(
        parentColumn = "id",    // Die ID aus der Challenge-Klasse
        entityColumn = "challengeId" // Die Fremd-ID aus der Completion-Klasse
    )
    val completion: ChallengeCompletion // Der Abschluss dazu (Foto, Notiz, Datum)
)