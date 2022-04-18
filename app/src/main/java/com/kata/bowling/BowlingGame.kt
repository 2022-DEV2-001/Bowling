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
        if (frameList[ZERO].firstRollKnockedPins == INITIAL_VALUE) {
            frameList[ZERO].firstRollKnockedPins = knockedPins
        } else {
            frameList[ZERO].secondRollKnockedPins = knockedPins
        }
    }

    companion object {
        const val INITIAL_VALUE = -1
        const val ZERO = 0
        const val ONE = 1
        const val TEN = 10
    }
}
