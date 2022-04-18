package com.kata.bowling.repository

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.kata.bowling.BowlingGame
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
}
