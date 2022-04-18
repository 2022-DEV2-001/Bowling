package com.kata.bowling

import com.google.common.truth.Truth.assertThat
import com.kata.bowling.model.Frame
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
}
