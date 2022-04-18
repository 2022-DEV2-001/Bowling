package com.kata.bowling

import com.kata.bowling.model.Frame
import com.kata.bowling.utils.GameException
import com.kata.bowling.utils.hasStrike
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

    fun score(): Int {
        var score = 0
        frameList.forEachIndexed { index, frame ->
            score += when {
                frame.hasStrike() -> {
                    scoreStrike(index)
                }
                else -> frame.sum()
            }
        }
        return score
    }

    private fun bonusRollAvailable() =
        frameList.last().sum() >= TEN || firstRollInLastFrame().isAStrike()

    private fun addBonusRollToFrameList(knockedPins: Int) {
        if (firstRollInLastFrame().isAStrike() &&
            secondRollInLastFrame() == INITIAL_VALUE
        ) {
            frameList.last().secondRollKnockedPins = knockedPins
        } else {
            frameList.last().bonusRollKnockedPins = knockedPins
        }
    }

    private fun addKnockedPinsToFrameList(knockedPins: Int) {
        run breaking@{
            frameList.forEachIndexed { index, frame ->
                when {
                    frame.firstRollKnockedPins == INITIAL_VALUE -> {
                        if (knockedPins == TEN && !lastFrame(index)) {
                            frame.secondRollKnockedPins = ZERO
                        }
                        frame.firstRollKnockedPins = knockedPins
                        return@breaking
                    }
                    frame.secondRollKnockedPins == INITIAL_VALUE -> {
                        if (sumOfTwoRollsIsNotGreaterThanTen(
                                frame.firstRollKnockedPins,
                                knockedPins
                            )
                        ) {
                            frame.secondRollKnockedPins = knockedPins
                        } else throw GameException.SumOfPinsOutOfRange
                        return@breaking
                    }
                }
            }
        }
    }

    /**
     * if a roll is a strike the score for that roll will be sum of current roll
     * plus next two rolls
     */
    private fun scoreStrike(frameIndex: Int): Int {
        var strikeScore = 0
        strikeScore += when {
            !lastFrame(frameIndex) -> {
                scoreStrikeUntilLastFrame(frameIndex)
            }
            else -> {
                scoreStrikeForLastFrame(frameIndex)
            }
        }
        return strikeScore
    }

    private fun scoreStrikeUntilLastFrame(rollIndex: Int) =
        if (firstRollInFirstFollowingFrame(rollIndex).isAStrike() &&
            secondFollowingFrameAvailable(rollIndex)
        ) {
            TEN + firstRollInFirstFollowingFrame(rollIndex) +
                firstRollInSecondFollowingFrame(rollIndex)
        } else {
            TEN + firstRollInFirstFollowingFrame(rollIndex) +
                secondRollInFirstFollowingFrame(rollIndex)
        }

    private fun scoreStrikeForLastFrame(rollIndex: Int) =
        TEN + frameList[rollIndex].secondRollKnockedPins +
            frameList[rollIndex].bonusRollKnockedPins

    private fun firstRollInFirstFollowingFrame(frameIndex: Int) =
        frameList[frameIndex + ONE].firstRollKnockedPins

    private fun secondRollInFirstFollowingFrame(frameIndex: Int) =
        frameList[frameIndex + ONE].secondRollKnockedPins

    private fun firstRollInSecondFollowingFrame(rollIndex: Int) =
        frameList[rollIndex + 2].firstRollKnockedPins

    private fun secondFollowingFrameAvailable(rollIndex: Int) = !lastFrame(rollIndex + 1)

    private fun sumOfTwoRollsIsNotGreaterThanTen(
        firstRollKnockedPins: Int,
        secondRollKnockedPins: Int
    ) = firstRollKnockedPins + secondRollKnockedPins <= TEN

    private fun firstRollInLastFrame() = frameList.last().firstRollKnockedPins
    private fun secondRollInLastFrame() = frameList.last().secondRollKnockedPins
    private fun lastFrame(index: Int) = index == frameList.size - ONE

    companion object {
        const val INITIAL_VALUE = -1
        const val ZERO = 0
        const val ONE = 1
        const val TEN = 10
    }
}
