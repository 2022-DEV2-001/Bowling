package com.kata.bowling.utils

sealed class GameException : Throwable() {
    object KnockedPinsOutOfRange : GameException()
    object SumOfPinsOutOfRange : GameException()
    object MaxSizeReached : GameException()
}
