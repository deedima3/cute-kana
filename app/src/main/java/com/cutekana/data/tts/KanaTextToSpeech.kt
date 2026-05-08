package com.cutekana.data.tts

import android.content.Context
import android.speech.tts.TextToSpeech
import android.speech.tts.UtteranceProgressListener
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class KanaTextToSpeech @Inject constructor(
    private val context: Context
) {
    private var tts: TextToSpeech? = null
    private val _isReady = MutableStateFlow(false)
    val isReady: StateFlow<Boolean> = _isReady
    
    private val _isSpeaking = MutableStateFlow(false)
    val isSpeaking: StateFlow<Boolean> = _isSpeaking

    init {
        initializeTTS()
    }

    private fun initializeTTS() {
        tts = TextToSpeech(context) { status ->
            if (status == TextToSpeech.SUCCESS) {
                val result = tts?.setLanguage(Locale.JAPANESE)
                if (result == TextToSpeech.LANG_MISSING_DATA || result == TextToSpeech.LANG_NOT_SUPPORTED) {
                    // Fallback to English if Japanese not available
                    tts?.setLanguage(Locale.US)
                }
                
                // Set speech rate slightly slower for learning
                tts?.setSpeechRate(0.8f)
                
                _isReady.value = true
                
                // Set up listener for speaking state
                tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                    override fun onStart(utteranceId: String?) {
                        _isSpeaking.value = true
                    }
                    
                    override fun onDone(utteranceId: String?) {
                        _isSpeaking.value = false
                    }
                    
                    override fun onError(utteranceId: String?) {
                        _isSpeaking.value = false
                    }
                })
            } else {
                _isReady.value = false
            }
        }
    }

    fun speak(text: String, onComplete: (() -> Unit)? = null) {
        if (!_isReady.value) {
            onComplete?.invoke()
            return
        }

        val utteranceId = UUID.randomUUID().toString()
        
        if (onComplete != null) {
            tts?.setOnUtteranceProgressListener(object : UtteranceProgressListener() {
                override fun onStart(utteranceId: String?) {
                    _isSpeaking.value = true
                }
                
                override fun onDone(utteranceId: String?) {
                    _isSpeaking.value = false
                    onComplete()
                }
                
                override fun onError(utteranceId: String?) {
                    _isSpeaking.value = false
                    onComplete()
                }
            })
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) {
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, null, utteranceId)
        } else {
            @Suppress("DEPRECATION")
            val params = HashMap<String, String>()
            params[TextToSpeech.Engine.KEY_PARAM_UTTERANCE_ID] = utteranceId
            tts?.speak(text, TextToSpeech.QUEUE_FLUSH, params)
        }
    }

    fun speakJapanese(text: String, onComplete: (() -> Unit)? = null) {
        speak(text, onComplete)
    }

    fun stop() {
        tts?.stop()
        _isSpeaking.value = false
    }

    fun shutdown() {
        tts?.stop()
        tts?.shutdown()
    }
}
