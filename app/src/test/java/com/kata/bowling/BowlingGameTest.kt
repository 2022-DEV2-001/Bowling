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

    /**If the sum of two rolls in a frame is equal to 10
     * it is called a spare
     * in final frame if  a spare is present, then bonus roll is available
     */
    @Test
    fun `given spare in last frame and roll, when get frame list, then updated to be returned with bonus roll`() {
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
            Frame(3, 7, 10),
        )

        roll(listOf(5, 4, 10, 10, 9, 1, 8, 2, 7, 2, 6, 3, 3, 4, 5, 4, 3, 7, 10))
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `given last frame is neither a strike nor a spare, when roll, then updated frame list to be returned without bonus roll`() {
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
            Frame(3, 6)
        )

        roll(listOf(5, 4, 10, 10, 9, 1, 8, 2, 7, 2, 6, 3, 3, 4, 5, 4, 3, 6))
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    /**
     * the sum of two rolls in a frame cannot be greater than 10
     * as there are only ten pins per frame
     */
    @Test(expected = GameException.SumOfPinsOutOfRange::class)
    fun `given knocked pins (5,6), when roll, then throw exception`() {
        game.roll(5)
        game.roll(6)
    }

    /**
     * in other frames if the first roll is a strike second roll is not available
     * but in last frame if the first roll is a strike then second roll is available
     */
    @Test
    fun `given first attempt in 10th frame is a strike, when get frame list, the updated list returned is returned with initial value for second roll`() {
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
            Frame(10, -1),
        )

        roll(listOf(5, 4, 10, 10, 9, 1, 8, 2, 7, 2, 6, 3, 3, 4, 5, 4, 10))
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    /**
     * when no pin is knocked in a roll it is called as a miss
     */
    @Test
    fun `given miss in all frames, when score, then returns score 0`() {
        val expectedResult = 0

        for (i in 0..19) {
            game.roll(0)
        }
        val result = game.score()

        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `given one pin knocked in 20 times, when score, then returns score 1`() {
        val expectedResult = 1

        game.roll(1)
        for (i in 0..18) {
            game.roll(0)
        }
        val result = game.score()

        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `given 4 pins knocked for in each roll in all frames, when score, then returns score 80`() {
        val expectedResult = 80

        roll(listOf(4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4, 4))
        val result = game.score()

        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `given first roll is a strike, when score, returns expected score 28`() {
        val expectedResult = 28

        roll(listOf(10, 5, 4, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
        val result = game.score()

        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `given first three rolls are strike, when score, returns score with sum of rolls in two following frames`() {
        val expectedResult = 60
        roll(listOf(10, 10, 10, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0))
        val result = game.score()

        assertThat(expectedResult).isEqualTo(result)
    }

    @Test
    fun `given all rolls are strike, when score, returns expected score`() {
        val expectedResult = 300

        roll(listOf(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 10))
        val result = game.score()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `given all rolls are spare, when score, returns expected score`() {
        val expectedResult = 150

        roll(listOf(5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5, 5))
        val result = game.score()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test
    fun `given random rolls, when score, returns expected score`() {
        val expectedResult = 127

        roll(listOf(10, 9, 1, 6, 4, 3, 6, 8, 2, 4, 5, 3, 6, 5, 5, 4, 5, 5, 5, 4))
        val result = game.score()

        assertThat(result).isEqualTo(expectedResult)
    }

    @Test(expected = GameException.MaxSizeReached::class)
    fun `given rolls with excess attempt, when score, then throw exception`() {
        roll(listOf(10, 10, 10, 9, 1, 3, 4, 2, 6, 7, 3, 9, 1, 5, 4, 4, 4, 4, 4))
    }

    @Test(expected = GameException.FramesNotFilled::class)
    fun `given only few frames filled, when score, then throw exception`() {
        roll(listOf(10, 10, 10, 9))
        game.score()
    }

    @Test(expected = GameException.SumOfPinsOutOfRange::class)
    fun `given second roll in last frame and bonus roll, when score, then throw exception`() {
        roll(listOf(10, 10, 10, 10, 10, 10, 10, 10, 10, 10, 5, 6))
        game.score()
    }

    @Test
    fun `given frames filled, when reset, then frames to be set to initial value`() {
        val expectedList = arrayListOf<Frame>()
        expectedList.initialValues()

        roll(listOf(10, 9, 1, 6, 4, 3, 6, 8, 2, 4, 5, 3, 6, 5, 5, 4, 5, 5, 5, 4))
        game.resetFrameList()
        val result = game.getFrameList()

        assertThat(expectedList).isEqualTo(result)
    }

    private fun roll(list: List<Int>) {
        list.forEach {
            game.roll(it)
        }
    }
}
