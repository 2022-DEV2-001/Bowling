package com.kata.bowling.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kata.bowling.BowlingGame
import com.kata.bowling.model.Frame
import com.kata.bowling.utils.initialValues
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.verify
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BowlingRepositoryTest {

    @RelaxedMockK
    private lateinit var bowlingGame: BowlingGame
    private lateinit var bowlingRepository: BowlingRepository

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        bowlingRepository = BowlingRepositoryImpl(bowlingGame)
    }

    @Test
    fun `given bowling repository, when get frame list, verify get frame list method is invoked `() {
        every { bowlingGame.getFrameList() } returns arrayListOf()

        bowlingRepository.getFrameList()

        verify {
            bowlingGame.getFrameList()
        }
    }

    @Test
    fun `given bowling repository, when get frame list, then frame list to be returned`() {
        val frameList = arrayListOf<Frame>()
        frameList.initialValues()
        every { bowlingGame.getFrameList() } returns frameList

        val result = bowlingRepository.getFrameList()

        assertThat(result).isEqualTo(frameList)
    }

    @Test
    fun `given bowling repository, when roll, verify roll method is invoked `() {
        bowlingRepository.roll(5)

        verify {
            bowlingGame.roll(5)
        }
    }

    @Test
    fun `given bowling repository, when get score, verify score method is invoked `() {
        bowlingRepository.getScore()

        verify {
            bowlingGame.score()
        }
    }
}
