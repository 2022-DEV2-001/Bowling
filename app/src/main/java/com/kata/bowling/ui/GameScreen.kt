package com.kata.bowling.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.*
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.kata.bowling.R
import com.kata.bowling.model.GameState
import com.kata.bowling.ui.components.FrameList
import com.kata.bowling.ui.components.PinsButton
import com.kata.bowling.ui.viewmodel.BowlingGameViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

const val PIN_SECTION_TEST_TAG = "pinSection"
const val FRAME_LIST_TEST_TAG = "FrameList"
const val SCORE_SECTION_TEST_TAG = "scoreSection"
const val SCORE_BUTTON_TEST_TAG = "scoreButton"
const val RESET_BUTTON_TEST_TAG = "resetButton"

@Composable
fun GameScreen(
    viewModel: BowlingGameViewModel = hiltViewModel()
) {
    val scaffoldState = rememberScaffoldState()
    val scope = rememberCoroutineScope()
    Scaffold(scaffoldState = scaffoldState) {
        Column(Modifier.fillMaxSize()) {
            val gameState by viewModel.gameState.observeAsState()
            gameState?.forEach { state ->
                Column(Modifier.weight(1f)) {
                    PinsSection {
                        viewModel.roll(it.toInt())
                    }
                    FrameSection(state)
                    ErrorSection(scope, scaffoldState, state.error)
                }
                ScoreSection(state.score)
                ButtonSection({
                    viewModel.getScore()
                }) {
                    viewModel.resetGame()
                }
            }
        }
    }
}

@Composable
private fun PinsSection(onPinSelected: (String) -> Unit) {
    Row(
        Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 10.dp)
            .testTag(PIN_SECTION_TEST_TAG),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        (0..10).forEach { text ->
            PinsButton(
                text = text.toString(),
                modifier = Modifier
                    .height(40.dp)
                    .fillMaxWidth()
                    .padding(horizontal = 5.dp)
                    .weight(1f)
            ) {
                onPinSelected(it)
            }
        }
    }
}

@Composable
private fun FrameSection(
    state: GameState
) {
    FrameList(
        frameList = state.frameList,
        modifier = Modifier
            .fillMaxWidth()
            .testTag(FRAME_LIST_TEST_TAG)
    )
}

@Composable
private fun ScoreSection(score: Int) {
    if (score != 0) {
        Text(
            modifier = Modifier
                .fillMaxWidth()
                .testTag(SCORE_SECTION_TEST_TAG),
            text = "${stringResource(R.string.score_message)} $score",
            style = TextStyle(
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        )
    }
}

@Composable
private fun ButtonSection(
    onScoreClicked: () -> Unit,
    onResetClicked: () -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        OutlinedButton(
            onClick = { onScoreClicked() },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
                .testTag(SCORE_BUTTON_TEST_TAG)
        ) {
            Text(text = stringResource(R.string.calculate_score))
        }
        OutlinedButton(
            onClick = { onResetClicked() },
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 10.dp)
                .testTag(RESET_BUTTON_TEST_TAG)
        ) {
            Text(text = stringResource(R.string.reset_game))
        }
    }
}

@SuppressLint("CoroutineCreationDuringComposition")
@Composable
private fun ErrorSection(scope: CoroutineScope, scaffoldState: ScaffoldState, error: String) {
    if (error.isNotEmpty()) {
        scope.launch {
            scaffoldState.snackbarHostState.showSnackbar(error, actionLabel = "OK")
        }
    }
}
