package com.kata.bowling

import com.kata.bowling.model.Frame
import com.kata.bowling.utils.GameException
import com.kata.bowling.utils.initialValues
import com.kata.bowling.utils.isAStrike
import com.kata.bowling.utils.isInRange
import com.kata.bowling.utils.sum

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
            when {
                bonusRollAvailable() -> {
                    addBonusRollToFrameList(knockedPins)
                }
                else -> {
                    addKnockedPinsToFrameList(knockedPins)
                }
            }
        } else throw GameException.KnockedPinsOutOfRange
    }

    private fun bonusRollAvailable() =
        frameList.last().sum() >= TEN || firstRollInLastFrame().isAStrike()

    private fun addBonusRollToFrameList(knockedPins: Int) {
        frameList.last().bonusRollKnockedPins = knockedPins
    }

    private fun addKnockedPinsToFrameList(knockedPins: Int) {
        run breaking@{
            frameList.forEach { frame ->
                when {
                    frame.firstRollKnockedPins == INITIAL_VALUE -> {
                        if (knockedPins == TEN) {
                            frame.secondRollKnockedPins = ZERO
                        }
                        frame.firstRollKnockedPins = knockedPins
                        return@breaking
                    }
                    frame.secondRollKnockedPins == INITIAL_VALUE -> {
                        frame.secondRollKnockedPins = knockedPins
                        return@breaking
                    }
                }
            }
        }
    }

    private fun firstRollInLastFrame() = frameList.last().firstRollKnockedPins

    companion object {
        const val INITIAL_VALUE = -1
        const val ZERO = 0
        const val ONE = 1
        const val TEN = 10
    }
}
