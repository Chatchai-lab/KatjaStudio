package de.kataia.app.data

import de.kataia.app.model.Challenge

/**
 * Schnittstelle für den Zugriff auf Challenges
 */
interface ChallengeRepository {
    fun getAllChallenges(): List<Challenge>
    fun getChallengeById(id: String): Challenge?
}