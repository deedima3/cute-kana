package com.cutekana.data.model

import androidx.room.Entity
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Entity(tableName = "characters")
data class CharacterEntity(
    @PrimaryKey
    val id: String,
    val character: String,
    val romaji: String,
    val type: CharacterType,
    val row: String,
    val strokeCount: Int,
    val strokes: List<StrokeData>,
    val audioFileName: String?,
    val associatedCharacter: String?,  // Anime/Western character name
    val associatedDescription: String?,
    val associatedImageUrl: String?,
    val rarity: Rarity,
    val jlptLevel: JlptLevel?,
    val meanings: List<String>?,       // For kanji
    val onYomi: List<String>?,           // For kanji
    val kunYomi: List<String>?,         // For kanji
    val radicals: List<String>?,         // For kanji
    val isUnlocked: Boolean = false,
    val masteryLevel: Int = 0,          // 0-8 (SRS levels)
    val correctReviews: Int = 0,
    val incorrectReviews: Int = 0,
    val lastReviewDate: Long? = null,
    val nextReviewDate: Long? = null,
    val strokeAccuracy: Float = 0f
)

@Serializable
enum class CharacterType {
    HIRAGANA,
    KATAKANA,
    KANJI,
    GRAMMAR,
    JLPT_STUDY
}

@Serializable
enum class Rarity {
    N,      // Normal
    R,      // Rare
    SR,     // Super Rare
    SSR,    // Super Super Rare
    UR      // Ultra Rare
}

@Serializable
enum class JlptLevel {
    N5,
    N4,
    N3,
    N2,
    N1
}

@Serializable
data class StrokeData(
    val id: Int,
    val path: List<PointF>,
    val startPoint: PointF,
    val endPoint: PointF,
    val direction: StrokeDirection,
    val type: StrokeType
)

@Serializable
data class PointF(
    val x: Float,
    val y: Float
)

@Serializable
enum class StrokeDirection {
    LEFT_TO_RIGHT,
    RIGHT_TO_LEFT,
    TOP_TO_BOTTOM,
    BOTTOM_TO_TOP,
    TOP_TO_BOTTOM_CURVED,
    BOTTOM_TO_TOP_CURVED,
    LEFT_TO_RIGHT_CURVED,
    RIGHT_TO_LEFT_CURVED,
    LOOP,
    DIAGONAL_DOWN_RIGHT,
    DIAGONAL_DOWN_LEFT,
    DIAGONAL_UP_RIGHT,
    DIAGONAL_UP_LEFT,
    HOOK,
    DOT
}

@Serializable
enum class StrokeType {
    HORIZONTAL,
    VERTICAL,
    CURVE,
    LOOP,
    DOT,
    HOOK,
    DIAGONAL,
    COMPLEX
}

// Converter for Room
data class WordEntity(
    val kanjiWriting: String,
    val kanaReading: String,
    val romaji: String,
    val meaning: String,
    val jlptLevel: JlptLevel,
    val exampleSentence: String?,
    val usesCharacters: List<String>
)