package com.kata.bowling.utils

import com.kata.bowling.BowlingGame
import com.kata.bowling.BowlingGame.Companion.ONE
import com.kata.bowling.BowlingGame.Companion.TEN
import com.kata.bowling.model.Frame

fun ArrayList<Frame>.initialValues() =
    repeat((ONE..TEN).count()) {
        this.add(Frame(BowlingGame.INITIAL_VALUE, BowlingGame.INITIAL_VALUE))
    }
