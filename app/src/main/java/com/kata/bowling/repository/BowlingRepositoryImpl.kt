package com.kata.bowling.repository

import com.kata.bowling.BowlingGame
import com.kata.bowling.model.Frame

class BowlingRepositoryImpl(private val bowlingGame: BowlingGame) : BowlingRepository {
    override fun getFrameList(): List<Frame> {
        return bowlingGame.getFrameList()
    }

    override fun roll(knockedPins: Int) {
        bowlingGame.roll(knockedPins)
    }

    override fun getScore(): Int {
        return bowlingGame.score()
    }
}
