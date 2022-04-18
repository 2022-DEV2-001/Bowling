package com.kata.bowling

import com.google.common.truth.Truth.assertThat
import com.kata.bowling.model.Frame
import org.junit.Test

class BowlingGameTest {
    private val game = BowlingGame()

    @Test
    fun `given game, when get frame list, then initial frame list to be returned`() {
        val expectedList = arrayListOf<Frame>()
        repeat((1..10).count()) {
            expectedList.add(Frame(-1, -1))
        }

        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }

    @Test
    fun `given 5 pins knocked for first roll in first frame, when get frame list, then updated frameList to be returned`() {
        val expectedList = arrayListOf<Frame>()
        repeat((1..10).count()) {
            expectedList.add(Frame(-1, -1))
        }
        expectedList[0].firstRollKnockedPins = 5

        game.roll(5)
        val result = game.getFrameList()

        assertThat(result).isEqualTo(expectedList)
    }
}
