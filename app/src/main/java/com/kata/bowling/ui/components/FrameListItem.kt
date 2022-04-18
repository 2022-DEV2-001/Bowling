package com.kata.bowling.ui.components

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kata.bowling.model.Frame

@Composable
fun FrameListItem(
    modifier: Modifier = Modifier,
    frame: Frame,
    isLastFrame: Boolean
) {
    Row(
        modifier = modifier
            .fillMaxSize()
            .border(1.dp, color = Color.Black),
        horizontalArrangement = Arrangement.SpaceEvenly,
    ) {
        Score(
            if (frame.firstRollKnockedPins != -1)
                frame.firstRollKnockedPins.toString()
            else "",
            modifier = Modifier.weight(1f)
        )
        if (frame.secondRollKnockedPins != -1 &&
            frame.firstRollKnockedPins != 10 && !isLastFrame
        ) {
            Score(frame.secondRollKnockedPins.toString(), modifier = Modifier.weight(1f))
        }
        if (isLastFrame && frame.secondRollKnockedPins != -1) {
            Score(frame.secondRollKnockedPins.toString(), modifier = Modifier.weight(1f))
        }
        if (isLastFrame && frame.bonusRollKnockedPins != -1
        ) {
            Score(frame.bonusRollKnockedPins.toString(), modifier = Modifier.weight(1f))
        }
    }
}

@Composable
private fun Score(score: String, modifier: Modifier = Modifier) {
    Text(
        modifier = modifier.padding(vertical = 5.dp),
        text = score,
        style = TextStyle(textAlign = TextAlign.Center, fontSize = 16.sp),
    )
}
