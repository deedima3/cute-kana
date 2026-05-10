package com.cutekana.data.audio

import android.media.ToneGenerator
import android.media.AudioManager
import kotlinx.coroutines.*

/**
 * Dopamine-inducing sound effects for the game.
 * Uses ToneGenerator to create pleasant synthesized sounds.
 */
object SoundEffectManager {
    
    private var toneGenerator: ToneGenerator? = null
    private val scope = CoroutineScope(SupervisorJob() + Dispatchers.Main)
    
    fun initialize() {
        if (toneGenerator == null) {
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        }
    }
    
    fun release() {
        toneGenerator?.release()
        toneGenerator = null
        scope.cancel()
    }
    
    /**
     * Dopamine-inducing correct answer sound.
     * A pleasant ascending major chord arpeggio that creates satisfaction.
     */
    fun playCorrect() {
        val tg = toneGenerator ?: ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        
        // Play a cheerful major chord arpeggio: C - E - G - High C
        // This creates a "success" feeling similar to mobile games
        scope.launch {
            tg.startTone(ToneGenerator.TONE_DTMF_1, 80) // C (523 Hz)
            delay(60)
            tg.startTone(ToneGenerator.TONE_DTMF_3, 80) // E (659 Hz)
            delay(60)
            tg.startTone(ToneGenerator.TONE_DTMF_5, 100) // G (784 Hz)
            delay(70)
            tg.startTone(ToneGenerator.TONE_DTMF_8, 150) // High C (1047 Hz)
        }
    }
    
    /**
     * Satisfying combo/streak sound for multiple correct answers.
     * Higher pitched, faster arpeggio for increased dopamine.
     */
    fun playCombo() {
        val tg = toneGenerator ?: ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        
        // Faster, higher-pitched arpeggio for streaks
        scope.launch {
            tg.startTone(ToneGenerator.TONE_DTMF_3, 50) // E
            delay(40)
            tg.startTone(ToneGenerator.TONE_DTMF_5, 50) // G
            delay(40)
            tg.startTone(ToneGenerator.TONE_DTMF_8, 50) // High C
            delay(40)
            tg.startTone(ToneGenerator.TONE_DTMF_9, 120) // Higher D
        }
    }
    
    /**
     * Dopamine-aware wrong answer sound.
     * A short, non-jarring "thud" that acknowledges the mistake
     * without being punishing or anxiety-inducing.
     */
    fun playWrong() {
        val tg = toneGenerator ?: ToneGenerator(AudioManager.STREAM_MUSIC, 85)
        
        // Low, quick "thud" sound - two descending low tones
        // Not harsh, just informative
        scope.launch {
            tg.startTone(ToneGenerator.TONE_DTMF_D, 100) // Low A (880 Hz lowered)
            delay(80)
            tg.startTone(ToneGenerator.TONE_DTMF_S, 120) // Even lower for thud effect
        }
    }
    
    /**
     * Match success sound for pair matching games.
     * A pleasant "pop" sound with harmonics.
     */
    fun playMatch() {
        val tg = toneGenerator ?: ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        
        scope.launch {
            tg.startTone(ToneGenerator.TONE_DTMF_5, 60) // G
            delay(40)
            tg.startTone(ToneGenerator.TONE_DTMF_6, 100) // A
        }
    }
    
    /**
     * Level complete / victory sound.
     * A longer, more celebratory fanfare.
     */
    fun playVictory() {
        val tg = toneGenerator ?: ToneGenerator(AudioManager.STREAM_MUSIC, 100)
        
        scope.launch {
            // Ascending fanfare
            tg.startTone(ToneGenerator.TONE_DTMF_1, 100)
            delay(100)
            tg.startTone(ToneGenerator.TONE_DTMF_3, 100)
            delay(100)
            tg.startTone(ToneGenerator.TONE_DTMF_5, 100)
            delay(100)
            tg.startTone(ToneGenerator.TONE_DTMF_8, 200)
            delay(200)
            // Final chord
            tg.startTone(ToneGenerator.TONE_DTMF_5, 300)
        }
    }
    
    /**
     * Game over sound - neutral but clear.
     */
    fun playGameOver() {
        val tg = toneGenerator ?: ToneGenerator(AudioManager.STREAM_MUSIC, 80)
        
        scope.launch {
            tg.startTone(ToneGenerator.TONE_DTMF_0, 150)
            delay(150)
            tg.startTone(ToneGenerator.TONE_DTMF_S, 250)
        }
    }
    
    /**
     * Click/selection feedback sound.
     * Very short, subtle confirmation.
     */
    fun playClick() {
        val tg = toneGenerator ?: ToneGenerator(AudioManager.STREAM_MUSIC, 60)
        tg.startTone(ToneGenerator.TONE_DTMF_5, 30)
    }
}
