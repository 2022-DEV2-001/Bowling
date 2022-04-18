package com.kata.bowling.model

data class GameState(
    val score: Int = 0,
    val frameList: List<Frame> = emptyList(),
    val error: String = ""
)
