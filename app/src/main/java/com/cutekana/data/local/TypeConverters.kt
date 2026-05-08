package com.cutekana.data.local

import androidx.room.TypeConverter
import com.cutekana.data.model.*
import kotlinx.serialization.json.Json
import kotlinx.serialization.builtins.ListSerializer
import kotlinx.serialization.builtins.MapSerializer
import kotlinx.serialization.builtins.serializer

// Type Converters for Room Database

class StrokeConverter {
    @TypeConverter
    fun fromStrokeList(strokes: List<StrokeData>): String {
        return Json.encodeToString(ListSerializer(StrokeData.serializer()), strokes)
    }
    
    @TypeConverter
    fun toStrokeList(value: String): List<StrokeData> {
        return Json.decodeFromString(ListSerializer(StrokeData.serializer()), value)
    }
}

class RarityConverter {
    @TypeConverter
    fun fromRarity(rarity: Rarity): String = rarity.name
    
    @TypeConverter
    fun toRarity(value: String): Rarity = Rarity.valueOf(value)
}

class JlptLevelConverter {
    @TypeConverter
    fun fromJlptLevel(level: JlptLevel?): String? = level?.name
    
    @TypeConverter
    fun toJlptLevel(value: String?): JlptLevel? = value?.let { JlptLevel.valueOf(it) }
}

class CharacterTypeConverter {
    @TypeConverter
    fun fromType(type: CharacterType): String = type.name
    
    @TypeConverter
    fun toType(value: String): CharacterType = CharacterType.valueOf(value)
}

class QuestionListConverter {
    @TypeConverter
    fun fromQuestionList(questions: List<QuestionData>): String {
        return Json.encodeToString(ListSerializer(QuestionData.serializer()), questions)
    }
    
    @TypeConverter
    fun toQuestionList(value: String): List<QuestionData> {
        return Json.decodeFromString(ListSerializer(QuestionData.serializer()), value)
    }
}

class TestModeConverter {
    @TypeConverter
    fun fromTestMode(mode: TestMode): String = mode.name
    
    @TypeConverter
    fun toTestMode(value: String): TestMode = TestMode.valueOf(value)
}

class SectionTypeConverter {
    @TypeConverter
    fun fromSectionType(type: SectionType): String = type.name
    
    @TypeConverter
    fun toSectionType(value: String): SectionType = SectionType.valueOf(value)
}

class TaskTypeConverter {
    @TypeConverter
    fun fromTaskType(type: TaskType): String = type.name
    
    @TypeConverter
    fun toTaskType(value: String): TaskType = TaskType.valueOf(value)
}

class ThemePreferenceConverter {
    @TypeConverter
    fun fromThemePreference(pref: ThemePreference): String = pref.name
    
    @TypeConverter
    fun toThemePreference(value: String): ThemePreference = ThemePreference.valueOf(value)
}

class StringListConverter {
    @TypeConverter
    fun fromStringList(list: List<String>?): String? {
        return list?.let { Json.encodeToString(ListSerializer(String.serializer()), it) }
    }

    @TypeConverter
    fun toStringList(value: String?): List<String>? {
        return value?.let { Json.decodeFromString(ListSerializer(String.serializer()), it) }
    }
}

class WeeklyPlanListConverter {
    @TypeConverter
    fun fromWeeklyPlanList(list: List<WeeklyPlanData>?): String? {
        return list?.let { Json.encodeToString(ListSerializer(WeeklyPlanData.serializer()), it) }
    }

    @TypeConverter
    fun toWeeklyPlanList(value: String?): List<WeeklyPlanData>? {
        return value?.let { Json.decodeFromString(ListSerializer(WeeklyPlanData.serializer()), it) }
    }
}

class SectionScoreMapConverter {
    @TypeConverter
    fun fromSectionScoreMap(map: Map<SectionType, SectionScoreData>?): String? {
        return map?.let { Json.encodeToString(MapSerializer(SectionType.serializer(), SectionScoreData.serializer()), it) }
    }

    @TypeConverter
    fun toSectionScoreMap(value: String?): Map<SectionType, SectionScoreData>? {
        return value?.let { Json.decodeFromString(MapSerializer(SectionType.serializer(), SectionScoreData.serializer()), it) }
    }
}
