package com.kata.bowling.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kata.bowling.model.Frame
import com.kata.bowling.model.GameState
import com.kata.bowling.repository.BowlingRepository

class BowlingGameViewModel(private val bowlingRepository: BowlingRepository) : ViewModel() {

    private var frameList: List<Frame> = arrayListOf()

    private val _gameState = MutableLiveData<SnapshotStateList<GameState>>()
    val gameState: LiveData<SnapshotStateList<GameState>> get() = _gameState

    fun roll(knockedPins: Int) {
        bowlingRepository.roll(knockedPins)
        frameList = bowlingRepository.getFrameList()
        updateGameState(frameList)
    }

    private fun updateGameState(
        frameList: List<Frame>,
        score: Int = 0,
        error: String = ""
    ) {
        _gameState.postValue(
            mutableStateListOf(
                GameState(
                    frameList = frameList,
                    score = score,
                    error = error
                )
            )
        )
    }
}
