package com.kata.bowling.repository

import com.kata.bowling.model.Frame

interface BowlingRepository {
    fun getFrameList(): List<Frame>
}
