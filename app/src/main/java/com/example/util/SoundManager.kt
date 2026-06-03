package com.example.util

import android.content.Context
import android.media.AudioAttributes
import android.media.AudioManager
import android.media.SoundPool
import android.media.ToneGenerator
import android.util.Log
import com.example.data.local.NeoChemSettings

object SoundManager {
    private const val TAG = "SoundManager"
    private var soundPool: SoundPool? = null
    private var toneGenerator: ToneGenerator? = null
    
    // Loaded sound IDs mapped to friendly keys
    private val soundIds = mutableMapOf<String, Int>()
    
    // Prevent overlapping sounds with a cool timer guard and single channel play
    private var lastPlayTime = 0L
    private const val SOUND_COOL_DOWN = 150L // ms
    private var lastStreamId = -1

    init {
        try {
            val audioAttributes = AudioAttributes.Builder()
                .setUsage(AudioAttributes.USAGE_ASSISTANCE_SONIFICATION)
                .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
                .build()

            soundPool = SoundPool.Builder()
                .setMaxStreams(2) // Max 2 lightweight streams to prevent overlapping
                .setAudioAttributes(audioAttributes)
                .build()

            // Initialize ToneGenerator for ultra-lightweight, zero-memory system beep fallbacks
            toneGenerator = ToneGenerator(AudioManager.STREAM_MUSIC, 75)
        } catch (e: Exception) {
            Log.e(TAG, "Initialization of SoundManager failed: ${e.message}", e)
        }
    }

    // Optional loader function for custom resources in the future
    fun loadSound(context: Context, key: String, resId: Int) {
        val pool = soundPool ?: return
        try {
            soundIds[key] = pool.load(context.applicationContext, resId, 1)
        } catch (e: Exception) {
            Log.e(TAG, "Error loading sound resource: ${e.message}")
        }
    }

    private fun playSoundByKey(key: String, fallbackTone: Int, toneDuration: Int = 120) {
        // Guard check: sound setting enabled
        if (!NeoChemSettings.soundEnabled.value) return

        val now = System.currentTimeMillis()
        if (now - lastPlayTime < SOUND_COOL_DOWN) {
            // Drop sound event to avoid annoying overlap and multiple play noises
            return
        }
        lastPlayTime = now

        val pool = soundPool
        val soundId = soundIds[key]
        if (pool != null && soundId != null && soundId > 0) {
            try {
                // Stop previous active stream if there was any
                if (lastStreamId != -1) {
                    pool.stop(lastStreamId)
                }
                lastStreamId = pool.play(soundId, 1f, 1f, 1, 0, 1f)
                return
            } catch (e: Exception) {
                Log.e(TAG, "SoundPool play failed, falling back to tone: ${e.message}")
            }
        }

        // Extremely lightweight fallback: ToneGenerator (0MB heap / fully stable native sound)
        try {
            toneGenerator?.startTone(fallbackTone, toneDuration)
        } catch (e: Exception) {
            Log.e(TAG, "Tone production failed: ${e.message}")
        }
    }

    fun playClick() {
        playSoundByKey("click", ToneGenerator.TONE_PROP_BEEP, 80)
    }

    fun playSuccess() {
        playSoundByKey("success", ToneGenerator.TONE_PROP_ACK, 150)
    }

    fun playError() {
        playSoundByKey("error", ToneGenerator.TONE_PROP_NACK, 250)
    }

    fun playCorrect() {
        playSoundByKey("correct", ToneGenerator.TONE_CDMA_PIP, 120)
    }

    fun playIncorrect() {
        playSoundByKey("incorrect", ToneGenerator.TONE_PROP_NACK, 200)
    }

    fun playMateriCompleted() {
        playSoundByKey("materi_done", ToneGenerator.TONE_SUP_DIAL, 250)
    }

    fun playQuizCompleted() {
        playSoundByKey("quiz_done", ToneGenerator.TONE_PROP_ACK, 300)
    }

    fun playLevelUp() {
        playSoundByKey("level_up", ToneGenerator.TONE_CDMA_HIGH_L, 400)
    }

    fun playAchievementUnlocked() {
        playSoundByKey("achievement_earned", ToneGenerator.TONE_CDMA_KEYPAD_VOLUME_KEY_LITE, 350)
    }

    fun playMysteryBoxOpened() {
        playSoundByKey("mystery_box", ToneGenerator.TONE_CDMA_ALERT_CALL_GUARD, 350)
    }

    fun release() {
        try {
            soundPool?.release()
            soundPool = null
            toneGenerator?.release()
            toneGenerator = null
        } catch (e: Exception) {
            Log.e(TAG, "Release failed: ${e.message}")
        }
    }
}
