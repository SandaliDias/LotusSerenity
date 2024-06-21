package com.example.mobilegame

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class ScoreViewModel : ViewModel() {
    private val _score = MutableLiveData<Int>()
    val score: LiveData<Int>
        get() = _score

    init {
        _score.value = 0
    }

    fun incrementScore() {
        _score.value = (_score.value ?: 0) + 1
    }

    fun getScore(): Int {
        return _score.value ?: 0
    }
}