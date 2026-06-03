package com.example.ui.viewmodel

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import com.example.NeoChemApplication
import com.example.data.dummy.ChemistryData
import com.example.data.model.SubMateri
import com.example.data.repository.UserRepository
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class QuizViewModel(application: Application) : AndroidViewModel(application) {

    private val userRepository = (application as NeoChemApplication).repository

    private val _materiId = MutableStateFlow("")
    val materiId = _materiId.asStateFlow()

    private val _quizQuestions = MutableStateFlow<List<SubMateri>>(emptyList())
    val quizQuestions = _quizQuestions.asStateFlow()

    private val _currentIndex = MutableStateFlow(0)
    val currentIndex = _currentIndex.asStateFlow()

    private val _selectedOptionIndex = MutableStateFlow<Int?>(null)
    val selectedOptionIndex = _selectedOptionIndex.asStateFlow()

    private val _isAnswered = MutableStateFlow(false)
    val isAnswered = _isAnswered.asStateFlow()

    private val _score = MutableStateFlow(0)
    val score = _score.asStateFlow()

    private val _comboCount = MutableStateFlow(0)
    val comboCount = _comboCount.asStateFlow()

    private val _timeLeft = MutableStateFlow(20)
    val timeLeft = _timeLeft.asStateFlow()

    private val _isFinished = MutableStateFlow(false)
    val isFinished = _isFinished.asStateFlow()

    private val _xpAwarded = MutableStateFlow(0)
    val xpAwarded = _xpAwarded.asStateFlow()

    private val _lastAnswerWasFast = MutableStateFlow(false)
    val lastAnswerWasFast = _lastAnswerWasFast.asStateFlow()

    // Lives limit system
    private val _lives = MutableStateFlow(3)
    val lives = _lives.asStateFlow()

    private var timerJob: Job? = null

    fun startQuiz(materiId: String) {
        _materiId.value = materiId
        _currentIndex.value = 0
        _selectedOptionIndex.value = null
        _isAnswered.value = false
        _score.value = 0
        _comboCount.value = 0
        _isFinished.value = false
        _xpAwarded.value = 0
        _lastAnswerWasFast.value = false
        _lives.value = 3
        
        // Find categories submaterials questions
        val cat = ChemistryData.categories.find { it.id == materiId }
        val ques = cat?.subMateriList ?: emptyList()
        // Strictly load maximum 10 questions for lightweight memory profiles
        _quizQuestions.value = ques.take(10)
        
        startTimer()
    }

    private fun startTimer() {
        timerJob?.cancel()
        _timeLeft.value = 20
        _lastAnswerWasFast.value = false
        timerJob = viewModelScope.launch {
            while (_timeLeft.value > 0 && !_isAnswered.value) {
                delay(1000)
                _timeLeft.value -= 1
            }
            if (_timeLeft.value == 0 && !_isAnswered.value) {
                // Time up! Auto fail question
                submitAnswer(-1)
            }
        }
    }

    fun submitAnswer(optionIdx: Int) {
        if (_isAnswered.value) return
        
        _selectedOptionIndex.value = optionIdx
        _isAnswered.value = true
        timerJob?.cancel()

        val currentQ = _quizQuestions.value.getOrNull(_currentIndex.value) ?: return
        if (optionIdx == currentQ.challengeAnswerIndex) {
            // Correct answer - Play Success Sound
            com.example.util.SoundManager.playSuccess()
            _score.value += 1
            _comboCount.value += 1
            
            // Speed bonus: if answered within 5 seconds (i.e. timeLeft is >= 15 seconds)
            val isFast = _timeLeft.value >= 15
            _lastAnswerWasFast.value = isFast
            val speedBonus = if (isFast) 15 else 0
            
            // XP formula: base 25 XP + combo bonus + Speed bonus
            val qXp = 25 + (_comboCount.value * 5) + speedBonus
            _xpAwarded.value += qXp
        } else {
            // Incorrect - Play Error Sound
            com.example.util.SoundManager.playError()
            _comboCount.value = 0
            _lastAnswerWasFast.value = false
            
            // Reduce 1 life!
            if (_lives.value > 0) {
                _lives.value -= 1
            }
        }
    }

    fun resetQuizState() {
        timerJob?.cancel()
        _materiId.value = ""
        _quizQuestions.value = emptyList()
        _currentIndex.value = 0
        _selectedOptionIndex.value = null
        _isAnswered.value = false
        _score.value = 0
        _comboCount.value = 0
        _lives.value = 3
        _isFinished.value = false
        _xpAwarded.value = 0
        _lastAnswerWasFast.value = false
    }

    fun nextQuestion() {
        if (!_isAnswered.value) return
        
        if (_lives.value <= 0) {
            finishQuiz()
            return
        }
        
        proceedToNext()
    }

    private fun proceedToNext() {
        val nextIdx = _currentIndex.value + 1
        if (nextIdx < _quizQuestions.value.size) {
            _currentIndex.value = nextIdx
            _selectedOptionIndex.value = null
            _isAnswered.value = false
            startTimer()
        } else {
            finishQuiz()
        }
    }

    private fun finishQuiz() {
        _isFinished.value = true
        viewModelScope.launch {
            val finalScorePercent = if (_quizQuestions.value.isNotEmpty()) {
                (_score.value.toDouble() / _quizQuestions.value.size * 100).toInt()
            } else 0
            
            userRepository.completeMateri(_materiId.value, finalScorePercent)
            userRepository.earnXp(_xpAwarded.value)

            val firebaseRepo = (getApplication() as NeoChemApplication).firebaseRepository
            if (firebaseRepo.getCurrentUser() != null) {
                try {
                    firebaseRepo.gainXpAndAdjustLevel(_xpAwarded.value, finalScorePercent)
                    firebaseRepo.completeMaterial(_materiId.value)
                } catch (e: Exception) {
                    android.util.Log.e("QuizViewModel", "Firebase quiz completion sync error", e)
                }
            }
        }
    }
}
