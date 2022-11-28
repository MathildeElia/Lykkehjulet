package com.example.wheel

data class State(
    val isSpinning: Boolean = true,
    val isGuessing: Boolean = false,
    val hasWon: Boolean = false,
    val hasLost: Boolean = false
)

val State.canSpin: Boolean get() = isSpinning
