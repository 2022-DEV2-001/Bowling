package com.kata.bowling

import com.kata.bowling.model.Frame
import com.kata.bowling.utils.initialValues

class BowlingGame {
    private val frameList = arrayListOf<Frame>()

    init {
        frameList.initialValues()
    }

    fun getFrameList(): List<Frame> {
        return frameList
    }

    fun roll(knockedPins: Int) {
        run breaking@{
            frameList.forEach { frame ->
                if (frame.firstRollKnockedPins == INITIAL_VALUE) {
                    frame.firstRollKnockedPins = knockedPins
                    return@breaking
                } else if (frame.secondRollKnockedPins == INITIAL_VALUE) {
                    frame.secondRollKnockedPins = knockedPins
                    return@breaking
                }
            }
        }
    }

    companion object {
        const val INITIAL_VALUE = -1
        const val ZERO = 0
        const val ONE = 1
        const val TEN = 10
    }
}
