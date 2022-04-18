package com.kata.bowling

import com.kata.bowling.model.Frame
import com.kata.bowling.utils.GameException
import com.kata.bowling.utils.hasSpare
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
        if (maxLengthReached()) {
            var score = ZERO
            frameList.forEachIndexed { index, frame ->
                score += when {
                    frame.hasStrike() -> {
                        scoreStrike(index)
                    }
                    frame.hasSpare() -> {
                        scoreSpare(index)
                    }
                    else -> frame.sum()
                }
            }
            return score
        } else throw GameException.FramesNotFilled
    }

    fun resetFrameList() {
        frameList.clear()
        frameList.initialValues()
    }

    private fun bonusRollAvailable() =
        frameList.last().sum() >= TEN || firstRollInLastFrame().isAStrike()

    private fun addBonusRollToFrameList(knockedPins: Int) {
        when {
            firstRollInLastFrame().isAStrike() &&
                secondRollInLastFrame() == INITIAL_VALUE -> {
                frameList.last().secondRollKnockedPins = knockedPins
            }
            bonusRollHasInitialValue() -> {
                addBonusRollKnockedPinsToFrame(knockedPins)
            }
            else -> {
                throw GameException.MaxSizeReached
            }
        }
    }

    private fun addBonusRollKnockedPinsToFrame(knockedPins: Int) {
        if (secondRollInLastFrame().isAStrike() ||
            sumOfTwoRollsIsNotGreaterThanTen(secondRollInLastFrame(), knockedPins) ||
            frameList.last().hasSpare()
        ) {
            frameList.last().bonusRollKnockedPins = knockedPins
        } else throw GameException.SumOfPinsOutOfRange
    }

    private fun addKnockedPinsToFrameList(knockedPins: Int) {
        if (!maxLengthReached()) {
            run breaking@{
                frameList.forEachIndexed { index, frame ->
                    when {
                        frame.firstRollKnockedPins == INITIAL_VALUE -> {
                            addKnockedPinsToFirstRollInFrame(knockedPins, index, frame)
                            return@breaking
                        }
                        frame.secondRollKnockedPins == INITIAL_VALUE -> {
                            addKnockedPinsToSecondRollInFrame(frame, knockedPins)
                            return@breaking
                        }
                    }
                }
            }
        } else throw GameException.MaxSizeReached
    }

    private fun addKnockedPinsToSecondRollInFrame(
        frame: Frame,
        knockedPins: Int
    ) {
        if (sumOfTwoRollsIsNotGreaterThanTen(
                frame.firstRollKnockedPins,
                knockedPins
            )
        ) {
            frame.secondRollKnockedPins = knockedPins
        } else throw GameException.SumOfPinsOutOfRange
    }

    private fun addKnockedPinsToFirstRollInFrame(
        knockedPins: Int,
        index: Int,
        frame: Frame
    ) {
        if (knockedPins == TEN && !lastFrame(index)) {
            frame.secondRollKnockedPins = ZERO
        }
        frame.firstRollKnockedPins = knockedPins
    }

    /**
     * The maximum rolls for a game is 20 if the last frame does
     * not has any strike or a spare
     * else the maximum rolls for a game is 21
     */
    private fun maxLengthReached(): Boolean {
        return when {
            bonusRollAvailable() -> {
                firstRollInLastFrame() != INITIAL_VALUE &&
                    frameList.last().secondRollKnockedPins != INITIAL_VALUE &&
                    !bonusRollHasInitialValue()
            }
            else -> {
                firstRollInLastFrame() != INITIAL_VALUE &&
                    frameList.last().secondRollKnockedPins != INITIAL_VALUE
            }
        }
    }

    /**
     * if a roll is a strike the score for that roll will be sum of current roll
     * plus next two rolls
     */
    private fun scoreStrike(frameIndex: Int): Int {
        var strikeScore = ZERO
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

    /**
     * if the sum of two rolls in a frame is a spare
     * then add the knocked pins in the following roll to current frame
     * if the sum of two in a last frame is a spare
     * then add bonus roll knocked pins
     */
    private fun scoreSpare(frameIndex: Int): Int {
        var spareScore = TEN
        spareScore += if (!lastFrame(frameIndex))
            firstRollInFirstFollowingFrame(frameIndex)
        else
            bonusRollInCurrentFrame(frameIndex)
        return spareScore
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
        TEN + secondRollInCurrentFrame(rollIndex) +
            bonusRollInCurrentFrame(rollIndex)

    private fun secondRollInCurrentFrame(rollIndex: Int) =
        frameList[rollIndex].secondRollKnockedPins

    private fun bonusRollInCurrentFrame(rollIndex: Int) =
        frameList[rollIndex].bonusRollKnockedPins

    private fun firstRollInFirstFollowingFrame(frameIndex: Int) =
        frameList[frameIndex + ONE].firstRollKnockedPins

    private fun secondRollInFirstFollowingFrame(frameIndex: Int) =
        frameList[frameIndex + ONE].secondRollKnockedPins

    private fun firstRollInSecondFollowingFrame(rollIndex: Int) =
        frameList[rollIndex + TWO].firstRollKnockedPins

    private fun secondFollowingFrameAvailable(rollIndex: Int) = !lastFrame(rollIndex + 1)

    private fun sumOfTwoRollsIsNotGreaterThanTen(
        firstRollKnockedPins: Int,
        secondRollKnockedPins: Int
    ) = firstRollKnockedPins + secondRollKnockedPins <= TEN

    private fun firstRollInLastFrame() = frameList.last().firstRollKnockedPins
    private fun secondRollInLastFrame() = frameList.last().secondRollKnockedPins
    private fun lastFrame(index: Int) = index == frameList.size - ONE
    private fun bonusRollHasInitialValue() =
        frameList.last().bonusRollKnockedPins == INITIAL_VALUE

    companion object {
        const val INITIAL_VALUE = -1
        const val ZERO = 0
        const val ONE = 1
        const val TWO = 2
        const val TEN = 10
    }
}
