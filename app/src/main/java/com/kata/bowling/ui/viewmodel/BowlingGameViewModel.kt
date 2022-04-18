package com.kata.bowling.ui.viewmodel

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.kata.bowling.R
import com.kata.bowling.model.Frame
import com.kata.bowling.model.GameState
import com.kata.bowling.repository.BowlingRepository
import com.kata.bowling.utils.GameException
import com.kata.bowling.utils.ResourcesProvider

class BowlingGameViewModel(
    private val bowlingRepository: BowlingRepository,
    private val resourcesProvider: ResourcesProvider
) : ViewModel() {

    private var frameList: List<Frame> = arrayListOf()

    private val _gameState = MutableLiveData<SnapshotStateList<GameState>>()
    val gameState: LiveData<SnapshotStateList<GameState>> get() = _gameState

    fun roll(knockedPins: Int) {
        runCatching {
            bowlingRepository.roll(knockedPins)
            frameList = bowlingRepository.getFrameList()
            updateGameState(frameList)
        }.getOrElse { t ->
            handleError(t)
        }
    }

    private fun handleError(t: Throwable) {
        when (t as GameException) {
            is GameException.KnockedPinsOutOfRange -> {
                updateErrorInGameState(resourcesProvider.getString(R.string.out_of_range_error))
            }
            is GameException.SumOfPinsOutOfRange -> {
                updateErrorInGameState(resourcesProvider.getString(R.string.sum_of_pins_error_message))
            }
            is GameException.MaxSizeReached -> {
                updateErrorInGameState(resourcesProvider.getString(R.string.max_size_reached_error))
            }
            is GameException.FramesNotFilled -> {
                updateErrorInGameState(resourcesProvider.getString(R.string.frames_not_filled_error))
            }
        }
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

    private fun updateErrorInGameState(error: String) {
        updateGameState(frameList, error = error)
    }
}
