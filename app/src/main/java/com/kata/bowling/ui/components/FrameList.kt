package com.kata.bowling.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kata.bowling.model.Frame

@Composable
fun FrameList(
    modifier: Modifier = Modifier,
    frameList: List<Frame>
) {
    LazyColumn(
        modifier
            .padding(vertical = 20.dp, horizontal = 10.dp)
    ) {
        itemsIndexed(frameList) { index, frame ->
            FrameListItem(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 5.dp),
                frame = frame,
                isLastFrame = index == frameList.size - 1
            )
        }
    }
}
