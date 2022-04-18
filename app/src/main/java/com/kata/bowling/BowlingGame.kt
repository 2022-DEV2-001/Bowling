package com.kata.bowling

import com.kata.bowling.model.Frame

class BowlingGame {
    private val frameList = arrayListOf<Frame>()

    init {
        repeat((ONE..TEN).count()) {
            frameList.add(Frame(INITIAL_VALUE, INITIAL_VALUE))
        }
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
