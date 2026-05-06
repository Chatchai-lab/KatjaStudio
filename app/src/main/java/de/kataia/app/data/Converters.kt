package de.kataia.app.data

import androidx.room.TypeConverter
import de.kataia.app.model.ChallengeCategory
import de.kataia.app.model.ChallengeDifficulty

class Converters {
    @TypeConverter
    fun fromCategory(value: ChallengeCategory): String = value.name

    @TypeConverter
    fun toCategory(value: String): ChallengeCategory = ChallengeCategory.valueOf(value)

    @TypeConverter
    fun fromDifficulty(value: ChallengeDifficulty): String = value.name

    @TypeConverter
    fun toDifficulty(value: String): ChallengeDifficulty = ChallengeDifficulty.valueOf(value)
}