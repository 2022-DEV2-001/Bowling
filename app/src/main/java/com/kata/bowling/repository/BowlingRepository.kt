package com.kata.bowling.repository

import com.kata.bowling.model.Frame

interface BowlingRepository {
    fun getFrameList(): List<Frame>
    fun roll(knockedPins: Int)
    fun getScore(): Int
    fun resetFrameList()
}
