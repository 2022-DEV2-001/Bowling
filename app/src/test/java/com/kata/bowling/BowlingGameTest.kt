package com.kata.bowling

import com.google.common.truth.Truth.assertThat
import com.kata.bowling.model.Frame
import com.kata.bowling.utils.GameException
import com.kata.bowling.utils.initialValues
import org.junit.Test

class BowlingGameTest {
    private val game = BowlingGame()

    @Test
    fun `given game, when get frame list, then initial frame list to be returned`() {
        val expectedList = arrayListOf<Frame>()
        expectedList.initialValues()

        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `given 5 pins knocked for first roll in first frame, when get frame list, then updated frameList to be returned`() {
        val expectedList = arrayListOf<Frame>()
        expectedList.initialValues()
        expectedList[0].firstRollKnockedPins = 5

        game.roll(5)
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `given first, second roll in first frame with pins knocked, when get frame list, then updated frameList to be returned`() {
        val expectedList = arrayListOf<Frame>()
        expectedList.initialValues()
        expectedList[0].firstRollKnockedPins = 5
        expectedList[0].secondRollKnockedPins = 4

        game.roll(5)
        game.roll(4)
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `given first,second roll in all frames with pins knocked, when get frame list, then updated frameList to be returned`() {
        val expectedList = arrayListOf<Frame>()
        expectedList.initialValues()
        for (list in expectedList) {
            list.firstRollKnockedPins = 5
            list.secondRollKnockedPins = 4
        }

        for (i in 0 until 10) {
            game.roll(5)
            game.roll(4)
        }
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `given first roll is a strike in first frame, when get frame list, then updated frame list to be returned with second roll value as 0`() {
        val expectedList = arrayListOf<Frame>()
        expectedList.initialValues()
        expectedList[0].firstRollKnockedPins = 10
        expectedList[0].secondRollKnockedPins = 0

        game.roll(10)
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `given pins knocked randomly in all frames, when get frame list, then updated frame list to be returned`() {
        val expectedList = listOf(
            Frame(5, 4),
            Frame(10, 0),
            Frame(10, 0),
            Frame(9, 1),
            Frame(8, 2),
            Frame(7, 2),
            Frame(6, 3),
            Frame(3, 4),
            Frame(5, 4),
            Frame(3, 1)
        )

        roll(listOf(5, 4, 10, 10, 9, 1, 8, 2, 7, 2, 6, 3, 3, 4, 5, 4, 3, 1))
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    /**
     * A frame contains only 10 pins
     * so a player can only knock 10 pins
     * more than that is impossible
     **/
    @Test(expected = GameException.KnockedPinsOutOfRange::class)
    fun `given 11 pins knocked in an attempt, when roll, should throw exception`() {
        game.roll(11)
    }

    @Test(expected = GameException.KnockedPinsOutOfRange::class)
    fun `given less than 0 pin knocked in an attempt, when roll, should throw exception`() {
        game.roll(-1)
    }

    private fun roll(list: List<Int>) {
        list.forEach {
            game.roll(it)
        }
    }
}
