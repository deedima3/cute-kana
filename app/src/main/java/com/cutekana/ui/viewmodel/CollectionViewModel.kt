package com.cutekana.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.cutekana.data.repository.CharacterRepository
import com.cutekana.data.repository.UserProgressRepository
import com.cutekana.ui.screens.collection.CollectionUiState
import com.cutekana.ui.screens.collection.CollectionCardItem
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CollectionViewModel @Inject constructor(
    private val characterRepository: CharacterRepository,
    private val userRepository: UserProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(CollectionUiState())
    val uiState: StateFlow<CollectionUiState> = _uiState.asStateFlow()

    init {
        loadCollection()
    }

    private fun loadCollection() {
        viewModelScope.launch {
            val kanaCards = characterRepository.getAllKana()
            val kanjiCards = characterRepository.getAllKanji()
            val compounds = characterRepository.getAllCompounds()
            val scenes = characterRepository.getAllScenes()
            
            val userProgress = userRepository.getUserProgress()
            
            _uiState.value = CollectionUiState(
                totalCards = kanaCards.size + kanjiCards.size + compounds.size + scenes.size,
                completionPercentage = 0.35f,
                premiumOrbs = userProgress.premiumOrbs,
                kanaCards = kanaCards.map { it.toCollectionItem() },
                kanjiCards = kanjiCards.map { it.toCollectionItem() },
                compoundCards = compounds.map { it.toCollectionItem() },
                sceneCards = scenes.map { it.toCollectionItem() }
            )
        }
    }

    fun openGacha() {
        _uiState.update { it.copy(showGachaScreen = true) }
    }

    fun closeGacha() {
        _uiState.update { it.copy(showGachaScreen = false) }
    }

    fun viewCardDetail(cardId: String) {
        // Find the card in any of the lists
        val allCards = _uiState.value.kanaCards + 
                      _uiState.value.kanjiCards + 
                      _uiState.value.compoundCards + 
                      _uiState.value.sceneCards
        val card = allCards.find { it.id == cardId }
        card?.let {
            _uiState.update { state ->
                state.copy(selectedCard = it)
            }
        }
    }

    fun closeCardDetail() {
        _uiState.update { it.copy(selectedCard = null) }
    }

    fun summon(summonType: Int) {
        viewModelScope.launch {
            // Deduct orbs based on summon type
            val cost = when (summonType) {
                1 -> 100
                2 -> 250
                3 -> 500
                else -> 100
            }
            
            // Unlock a random card based on summon type
            val unlockedCardId = when (summonType) {
                1 -> characterRepository.unlockRandomKana()
                2 -> characterRepository.unlockRandomN5Kanji()
                3 -> characterRepository.unlockRandomN4Kanji()
                else -> characterRepository.unlockRandomKana()
            }
            
            // Update user orbs
            userRepository.spendOrbs(cost)
            
            // Refresh collection
            loadCollection()
            
            // Show result
            unlockedCardId?.let { id ->
                val card = (uiState.value.kanaCards + uiState.value.kanjiCards + 
                           uiState.value.compoundCards + uiState.value.sceneCards)
                    .find { it.id == id }
                card?.let {
                    _uiState.update { state ->
                        state.copy(
                            showGachaScreen = false,
                            lastSummonedCard = it
                        )
                    }
                }
            }
        }
    }

    fun clearSummonResult() {
        _uiState.update { it.copy(lastSummonedCard = null) }
    }

    private fun com.cutekana.data.model.CharacterEntity.toCollectionItem() = CollectionCardItem(
        id = id,
        character = character,
        romaji = romaji,
        meaning = meanings?.firstOrNull(),
        associatedName = associatedCharacter,
        rarity = rarity,
        isUnlocked = isUnlocked,
        jlptLevel = jlptLevel?.name
    )
}

@HiltViewModel
class PlayViewModel @Inject constructor(
    private val userRepository: UserProgressRepository
) : ViewModel() {

    private val _uiState = MutableStateFlow(com.cutekana.ui.screens.play.PlayUiState())
    val uiState: StateFlow<com.cutekana.ui.screens.play.PlayUiState> = _uiState.asStateFlow()

    init {
        loadHighScores()
    }

    private fun loadHighScores() {
        viewModelScope.launch {
            val scores = userRepository.getHighScores()

            _uiState.value = com.cutekana.ui.screens.play.PlayUiState(
                radicalBuilderHighScore = scores["radical_builder"] ?: 0,
                kanjiStoryHighScore = scores["kanji_story"] ?: 0,
                matchPairsHighScore = scores["match_pairs"] ?: 0,
                speedQuizHighScore = scores["speed_quiz"] ?: 0,
                kanaGuessHighScore = scores["kana_guess"] ?: 0,
                wordGuessHighScore = scores["word_guess"] ?: 0
            )
        }
    }

    fun startGame(mode: com.cutekana.ui.screens.play.PlayGameMode) {
        // Navigation is now handled by the screen directly
        _uiState.update { currentState ->
            currentState.copy(
                selectedGame = mode,
                message = null
            )
        }
    }

    fun updateHighScore(mode: com.cutekana.ui.screens.play.PlayGameMode, score: Int) {
        viewModelScope.launch {
            userRepository.updateHighScore(mode.name.lowercase(), score)
            // Reload high scores
            val scores = userRepository.getHighScores()
            _uiState.update { currentState ->
                currentState.copy(
                    radicalBuilderHighScore = scores["radical_builder"] ?: currentState.radicalBuilderHighScore,
                    kanjiStoryHighScore = scores["kanji_story"] ?: currentState.kanjiStoryHighScore,
                    matchPairsHighScore = scores["match_pairs"] ?: currentState.matchPairsHighScore,
                    speedQuizHighScore = scores["speed_quiz"] ?: currentState.speedQuizHighScore,
                    kanaGuessHighScore = scores["kana_guess"] ?: currentState.kanaGuessHighScore,
                    wordGuessHighScore = scores["word_guess"] ?: currentState.wordGuessHighScore
                )
            }
        }
    }

    fun clearMessage() {
        _uiState.update { it.copy(message = null) }
    }
}