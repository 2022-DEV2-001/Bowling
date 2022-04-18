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
        frameList[0].firstRollKnockedPins = knockedPins
    }

    companion object {
        const val INITIAL_VALUE = -1
        const val ONE = 1
        const val TEN = 10
    }
}
