package com.kata.bowling.repository

import com.kata.bowling.BowlingGame
import com.kata.bowling.model.Frame
import javax.inject.Inject

class BowlingRepositoryImpl @Inject constructor
(private val bowlingGame: BowlingGame) : BowlingRepository {
    override fun getFrameList(): List<Frame> {
        return bowlingGame.getFrameList()
    }

    override fun roll(knockedPins: Int) {
        bowlingGame.roll(knockedPins)
    }

    override fun getScore(): Int {
        return bowlingGame.score()
    }

    override fun resetFrameList() {
        bowlingGame.resetFrameList()
    }
}
