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
}
