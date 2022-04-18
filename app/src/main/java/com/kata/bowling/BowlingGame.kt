package com.kata.bowling

import com.kata.bowling.model.Frame

class BowlingGame {
    private val frameList = arrayListOf<Frame>()

    init {
        repeat((1..10).count()) {
            frameList.add(Frame(-1, -1))
        }
    }

    fun getFrameList(): List<Frame> {
        return frameList
    }
}