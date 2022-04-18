package com.kata.bowling.ui.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kata.bowling.BowlingGame
import com.kata.bowling.model.Frame
import com.kata.bowling.repository.BowlingRepository
import com.kata.bowling.repository.BowlingRepositoryImpl
import com.kata.bowling.utils.initialValues
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BowlingGameViewModelTest {

    @RelaxedMockK
    private lateinit var bowlingGame: BowlingGame
    private lateinit var bowlingRepository: BowlingRepository
    private lateinit var viewModel: BowlingGameViewModel

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        bowlingRepository = BowlingRepositoryImpl(bowlingGame)
        viewModel = BowlingGameViewModel(bowlingRepository = bowlingRepository)
    }

    @Test
    fun `given view model , when roll and get frame list, then expected frame list to be returned`() {
        val frameList = arrayListOf<Frame>()
        frameList.initialValues()
        every { bowlingGame.getFrameList() } returns frameList

        viewModel.roll(5)

        var result: List<Frame> = arrayListOf()
        viewModel.gameState.value?.forEach { result = it.frameList }

        assertThat(result).isEqualTo(frameList)
    }
}
