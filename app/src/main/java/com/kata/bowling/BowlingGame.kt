package com.kata.bowling

import com.kata.bowling.model.Frame
import com.kata.bowling.utils.GameException
import com.kata.bowling.utils.initialValues
import com.kata.bowling.utils.isInRange

class BowlingGame {
    private val frameList = arrayListOf<Frame>()

    init {
        frameList.initialValues()
    }

    fun getFrameList(): List<Frame> {
        return frameList
    }

    fun roll(knockedPins: Int) {
        if (knockedPins.isInRange()) {
            if (bonusRollAvailable()) {
                frameList.last().bonusRollKnockedPins = knockedPins
            } else {
                run breaking@{
                    frameList.forEach { frame ->
                        if (frame.firstRollKnockedPins == INITIAL_VALUE) {
                            if (knockedPins == TEN) {
                                frame.secondRollKnockedPins = ZERO
                            }
                            frame.firstRollKnockedPins = knockedPins
                            return@breaking
                        } else if (frame.secondRollKnockedPins == INITIAL_VALUE) {
                            frame.secondRollKnockedPins = knockedPins
                            return@breaking
                        }
                    }
                }
            }
        } else throw GameException.KnockedPinsOutOfRange
    }

    private fun bonusRollAvailable() =
        frameList.last().firstRollKnockedPins +
            frameList.last().secondRollKnockedPins >= TEN ||
            frameList.last().firstRollKnockedPins == TEN

    companion object {
        const val INITIAL_VALUE = -1
        const val ZERO = 0
        const val ONE = 1
        const val TEN = 10
    }
}
