package com.kata.bowling.model

data class Frame(
    var firstRollKnockedPins: Int,
    var secondRollKnockedPins: Int,
    var bonusRollKnockedPins: Int = -1
)
