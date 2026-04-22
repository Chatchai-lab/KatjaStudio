package de.kataia.app.data

import de.kataia.app.model.Challenge
import de.kataia.app.model.ChallengeCategory
import de.kataia.app.model.ChallengeDifficulty

class FakeChallengeRepository : ChallengeRepository {

    private val fakeChallenges = listOf(
        Challenge(
            id = "1",
            title = "Kreise ziehen",
            description = "Zeichne 50 Kreise aus der Schulter heraus, um dein Warm-up zu starten.",
            difficulty = ChallengeDifficulty.EASY,
            category = ChallengeCategory.SHAPES,
            xpReward = 10,
            estimatedMinutes = 5
        ),
        Challenge(
            id = "2",
            title = "Die menschliche Hand",
            description = "Skizziere deine eigene linke Hand in drei verschiedenen Posen.",
            difficulty = ChallengeDifficulty.HARD,
            category = ChallengeCategory.ANATOMY,
            xpReward = 50,
            estimatedMinutes = 30
        ),
        Challenge(
            id = "3",
            title = "Kaffeetasse im Licht",
            description = "Stelle eine Tasse vor eine Lampe und zeichne nur die Schattenbereiche.",
            difficulty = ChallengeDifficulty.MEDIUM,
            category = ChallengeCategory.STILL_LIFE,
            xpReward = 25,
            estimatedMinutes = 15
        ),
        Challenge(
            id = "4",
            title = "Dein Traumhaus",
            description = "Nutze 2-Punkt-Perspektive, um ein Haus aus deiner Fantasie zu zeichnen.",
            difficulty = ChallengeDifficulty.MEDIUM,
            category = ChallengeCategory.CREATIVE,
            xpReward = 30,
            estimatedMinutes = 20
        ),
        Challenge(
            id = "5",
            title = "Schnelle Gesten",
            description = "Suche dir Fotos von Menschen und skizziere die Pose in jeweils nur 30 Sekunden.",
            difficulty = ChallengeDifficulty.EASY,
            category = ChallengeCategory.ANATOMY,
            xpReward = 15,
            estimatedMinutes = 10
        )
    )

    override fun getAllChallenges(): List<Challenge> = fakeChallenges

    override fun getChallengeById(id: String): Challenge? {
        return fakeChallenges.find { it.id == id }
    }
}