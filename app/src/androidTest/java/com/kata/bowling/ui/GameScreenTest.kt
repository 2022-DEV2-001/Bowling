package com.kata.bowling.ui

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createAndroidComposeRule
import com.kata.bowling.BowlingActivity
import com.kata.bowling.R
import com.kata.bowling.ui.theme.BowlingTheme
import dagger.hilt.android.testing.HiltAndroidRule
import dagger.hilt.android.testing.HiltAndroidTest
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@HiltAndroidTest
class GameScreenTest {
    @get:Rule(order = 0)
    val hiltRule = HiltAndroidRule(this)

    @get:Rule(order = 1)
    val composeRule = createAndroidComposeRule<BowlingActivity>()

    @Before
    fun setUp() {
        hiltRule.inject()
        composeRule.setContent {
            BowlingTheme() {
                GameScreen()
            }
        }
    }

    @Test
    fun checkIfAllComponentsAreDisplayedWithInitalValues() {
        composeRule.onNodeWithTag(PIN_SECTION_TEST_TAG)
            .onChildren()
            .assertCountEquals(11)
        composeRule.onNodeWithTag(PIN_SECTION_TEST_TAG)
            .onChildren()[1]
            .assertTextEquals("1")
        composeRule.onNodeWithTag(FRAME_LIST_TEST_TAG).onChildren()
            .assertCountEquals(10)
        composeRule.onNodeWithTag(FRAME_LIST_TEST_TAG).onChildren()[1]
            .assertTextEquals("")
        composeRule.onNodeWithTag(SCORE_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertTextEquals(composeRule.activity.getString(R.string.calculate_score))
        composeRule.onNodeWithTag(RESET_BUTTON_TEST_TAG)
            .assertIsDisplayed()
            .assertTextEquals(composeRule.activity.getString(R.string.reset_game))
    }

    @Test
    fun onClickingPinsTheFrameListSectionShouldBeUpdated() {
        composeRule.onNodeWithTag(PIN_SECTION_TEST_TAG)
            .onChildren()[3].performClick()
        composeRule.onNodeWithTag(FRAME_LIST_TEST_TAG)
            .onChildren()[0]
            .assertTextEquals("3")
    }
}
