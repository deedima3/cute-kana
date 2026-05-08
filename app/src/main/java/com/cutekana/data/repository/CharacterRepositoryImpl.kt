package com.cutekana.data.repository

import com.cutekana.data.local.CharacterDao
import com.cutekana.data.model.CharacterEntity
import com.cutekana.data.model.JlptLevel
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class CharacterRepositoryImpl @Inject constructor(
    private val characterDao: CharacterDao
) : CharacterRepository {
    
    override suspend fun getAllHiragana(): List<CharacterEntity> = 
        characterDao.getAllHiragana()
    
    override suspend fun getAllKatakana(): List<CharacterEntity> = 
        characterDao.getAllKatakana()
    
    override suspend fun getAllKanji(): List<CharacterEntity> = 
        characterDao.getAllKanji()
    
    override suspend fun getAllKana(): List<CharacterEntity> = 
        characterDao.getAllKana()
    
    override suspend fun getAllCompounds(): List<CharacterEntity> = 
        characterDao.getAllCompounds()
    
    override suspend fun getAllScenes(): List<CharacterEntity> = 
        characterDao.getAllScenes()
    
    override suspend fun getKanjiByLevel(level: JlptLevel): List<CharacterEntity> = 
        characterDao.getKanjiByLevel(level)
    
    override suspend fun getCharacterById(id: String): CharacterEntity = 
        characterDao.getCharacterById(id) ?: throw NoSuchElementException("Character not found: $id")
    
    override suspend fun getCharacterByChar(character: String): CharacterEntity = 
        characterDao.getCharacterByChar(character) ?: throw NoSuchElementException("Character not found: $character")
    
    override suspend fun getCharactersForGuidedPractice(): List<String> {
        return getAllHiragana().take(10).map { it.id }
    }
    
    override suspend fun getCharactersForFreeWriting(): List<String> {
        return (getAllHiragana() + getAllKatakana().take(10)).map { it.id }
    }
    
    override suspend fun getCharactersForSpeedChallenge(): List<String> {
        return getAllHiragana().take(20).map { it.id }
    }
    
    override suspend fun getCharactersForMemoryMode(): List<String> {
        return getAllHiragana().take(15).map { it.id }
    }
    
    override suspend fun getLearnedCharactersCount(): Int = 
        characterDao.getLearnedCharactersCount()
    
    override suspend fun getLearnedKanjiCount(): Int = 
        characterDao.getLearnedKanjiCount()
    
    override suspend fun getJlptProgress(level: JlptLevel): Float {
        val learned = characterDao.getLearnedKanjiCountByLevel(level).toFloat()
        val total = characterDao.getTotalKanjiCountByLevel(level).toFloat()
        return if (total > 0) learned / total else 0f
    }
    
    override suspend fun updateCharacterProgress(characterId: String, masteryLevel: Int) {
        val character = characterDao.getCharacterById(characterId) ?: return
        val updated = character.copy(masteryLevel = masteryLevel)
        characterDao.updateCharacter(updated)
    }
    
    override suspend fun unlockCharacter(characterId: String) {
        val character = characterDao.getCharacterById(characterId) ?: return
        val updated = character.copy(isUnlocked = true)
        characterDao.updateCharacter(updated)
    }
    
    override suspend fun unlockRandomKana(): String? {
        val lockedKana = getAllKana().filter { !it.isUnlocked }
        if (lockedKana.isEmpty()) return null
        val random = lockedKana.random()
        unlockCharacter(random.id)
        return random.id
    }
    
    override suspend fun unlockRandomN5Kanji(): String? {
        val lockedKanji = getKanjiByLevel(JlptLevel.N5).filter { !it.isUnlocked }
        if (lockedKanji.isEmpty()) {
            // Fallback to kana if no locked N5 kanji
            return unlockRandomKana()
        }
        val random = lockedKanji.random()
        unlockCharacter(random.id)
        return random.id
    }
    
    override suspend fun unlockRandomN4Kanji(): String? {
        val lockedKanji = getKanjiByLevel(JlptLevel.N4).filter { !it.isUnlocked }
        if (lockedKanji.isEmpty()) {
            // Fallback to N5 kanji
            return unlockRandomN5Kanji()
        }
        val random = lockedKanji.random()
        unlockCharacter(random.id)
        return random.id
    }
}