package com.cutekana.ui.viewmodel

import android.speech.tts.TextToSpeech
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutekana.data.repository.CharacterRepository
import com.cutekana.data.tts.KanaTextToSpeech
import com.cutekana.ui.screens.learn.LearnUiState
import com.cutekana.ui.screens.learn.LearnCategory
import com.cutekana.ui.screens.learn.LearnCharacterItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LearnViewModel @Inject constructor(
    private val characterRepository: CharacterRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(LearnUiState())
    val uiState: StateFlow<LearnUiState> = _uiState.asStateFlow()

    init {
        loadCategories()
    }

    private fun loadCategories() {
        viewModelScope.launch {
            // Load all categories
            val hiragana = characterRepository.getAllHiragana()
            val katakana = characterRepository.getAllKatakana()
            val n5Kanji = characterRepository.getKanjiByLevel(com.cutekana.data.model.JlptLevel.N5)
            val n4Kanji = characterRepository.getKanjiByLevel(com.cutekana.data.model.JlptLevel.N4)

            _uiState.value = _uiState.value.copy(
                hiraganaCharacters = hiragana.map { it.toLearnItem() },
                katakanaCharacters = katakana.map { it.toLearnItem() },
                n5Kanji = n5Kanji.map { it.toLearnItem() },
                n4Kanji = n4Kanji.map { it.toLearnItem() }
            )
        }
    }

    fun selectCategory(index: Int) {
        val category = when (index) {
            0 -> LearnCategory.Hiragana
            1 -> LearnCategory.Katakana
            2 -> LearnCategory.KanjiN5
            3 -> LearnCategory.KanjiN4
            4 -> LearnCategory.Grammar
            5 -> LearnCategory.JLPTN5Study
            6 -> LearnCategory.JLPTN4Study
            else -> LearnCategory.Hiragana
        }

        _uiState.value = _uiState.value.copy(
            selectedTabIndex = index,
            currentCategory = category
        )
    }

    private fun com.cutekana.data.model.CharacterEntity.toLearnItem() = LearnCharacterItem(
        character = character,
        romaji = romaji,
        associatedName = associatedCharacter,
        meaning = meanings?.firstOrNull(),
        rarity = rarity,
        isUnlocked = isUnlocked,
        masteryLevel = masteryLevel,
        type = type
    )
}

@HiltViewModel
class CharacterDetailViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val tts: KanaTextToSpeech
) : ViewModel() {

    private val _uiState = MutableStateFlow(com.cutekana.ui.screens.learn.CharacterDetailUiState())
    val uiState: StateFlow<com.cutekana.ui.screens.learn.CharacterDetailUiState> = _uiState.asStateFlow()
    
    val isTtsReady = tts.isReady
    val isSpeaking = tts.isSpeaking

    fun loadCharacter(character: String) {
        viewModelScope.launch {
            _uiState.value = _uiState.value.copy(isLoading = true)
            
            val char = characterRepository.getCharacterByChar(character)
            
            _uiState.value = com.cutekana.ui.screens.learn.CharacterDetailUiState(
                character = char.character,
                romaji = char.romaji,
                associatedName = char.associatedCharacter,
                associatedDescription = char.associatedDescription,
                meanings = char.meanings,
                onYomi = char.onYomi,
                kunYomi = char.kunYomi,
                strokeCount = char.strokeCount,
                isLoading = false
            )
        }
    }

    fun playAudio() {
        val character = _uiState.value.character
        if (character.isNotEmpty()) {
            tts.speakJapanese(character)
        }
    }
    
    fun stopAudio() {
        tts.stop()
    }
    
    override fun onCleared() {
        super.onCleared()
        tts.stop()
    }
}