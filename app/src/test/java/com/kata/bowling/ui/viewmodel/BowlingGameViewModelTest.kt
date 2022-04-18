package com.kata.bowling.ui.viewmodel

import android.app.Application
import android.content.Context
import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import com.google.common.truth.Truth.assertThat
import com.kata.bowling.BowlingGame
import com.kata.bowling.model.Frame
import com.kata.bowling.model.GameState
import com.kata.bowling.repository.BowlingRepository
import com.kata.bowling.repository.BowlingRepositoryImpl
import com.kata.bowling.utils.GameException
import com.kata.bowling.utils.ResourcesProvider
import com.kata.bowling.utils.initialValues
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.mockk.mockk
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class BowlingGameViewModelTest {

    @RelaxedMockK
    private lateinit var bowlingGame: BowlingGame
    private lateinit var bowlingRepository: BowlingRepository
    private lateinit var viewModel: BowlingGameViewModel
    private lateinit var context: Context
    private lateinit var resourcesProvider: ResourcesProvider

    @Rule
    @JvmField
    val rule = InstantTaskExecutorRule()

    @Before
    fun setUp() {
        MockKAnnotations.init(this)
        bowlingRepository = BowlingRepositoryImpl(bowlingGame)
        context = mockk<Application>()
        resourcesProvider = ResourcesProvider(context)
        viewModel = BowlingGameViewModel(bowlingRepository = bowlingRepository, resourcesProvider)
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

    @Test
    fun `given view model , when roll, then verify the exception thrown`() {
        every { bowlingGame.roll(5) } throws GameException.MaxSizeReached
        every { context.getString(any()) } returns "An error occurred"
        viewModel.roll(5)

        var result = ""
        viewModel.gameState.value?.forEach { result = it.error }

        assertThat(result).isNotEmpty()
    }

    @Test
    fun `given view model , when get score, then score to be returned`() {
        every { bowlingGame.score() } returns 150

        viewModel.getScore()

        var result = 0
        viewModel.gameState.value?.forEach { result = it.score }

        assertThat(result).isEqualTo(150)
    }

    @Test
    fun `given view model , when reset game, then game state should be returned to initial state`() {
        val frameList = arrayListOf<Frame>()
        frameList.initialValues()
        every { bowlingGame.getFrameList() } returns frameList

        viewModel.resetGame()
        var result = GameState()
        viewModel.gameState.value?.forEach { result = it }

        assertThat(result.frameList).isEqualTo(frameList)
        assertThat(result.score).isEqualTo(0)
        assertThat(result.error).isEqualTo("")
    }
}
