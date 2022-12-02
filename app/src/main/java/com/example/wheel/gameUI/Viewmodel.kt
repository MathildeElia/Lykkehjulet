package com.example.wheel.gameUI

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wheel.model.KeyboardLetter
import com.example.wheel.model.Letter
import com.example.wheel.model.State

class Viewmodel : ViewModel() {

    //stateflow
    var isStarted = false
    val lives = mutableStateOf(5)
    val values = arrayOf(0, 500, 600, 700, 800, 900, 10000)
    var curSpinValue = 0
    val spinValueString = mutableStateOf("")
    val points = mutableStateOf(0)
    val currentWord = mutableStateOf("")
    val currentCategory = mutableStateOf("")
    val allWords: Array<Array<String>> = arrayOf(
        arrayOf("Around the House", "Rhyme Time", "On The Map", "Occupation"),
        arrayOf("CANDLEHOLDERS", "SUPERDUPER", "QUEBEC", "ZOOKEEPER")
    )
    val currentState = mutableStateOf(State.SPIN)

    var keyboardLetters = ArrayList<KeyboardLetter>()
    val guessedLettersList = mutableStateOf("")
    val lettersInWord = mutableListOf<Letter>()

    val endMessage = mutableStateOf("")
    val winnerMessage = "You have guessed the word!\n" +
            "You WON!! Congratulations :) "
    val loseMessage = "You lost all your lives :( \n" +
            "You lost.. Try again"

    fun gameInit(){
        endMessage.value = ""
        isStarted = false
        lives.value = 5
        curSpinValue = 0
        spinValueString.value = ""
        points.value = 0
        currentState.value = State.SPIN
        keyboardLetters.clear()
        addValuesToKeyboard()
        guessedLettersList.value = ""
        lettersInWord.clear()
    }

    fun looseALife() {
        lives.value--
    }

    fun addValuesToKeyboard() {
        var c = 'A'
        while (c <= 'Z') {
            keyboardLetters.add(KeyboardLetter(c.toString()))
            ++c
        }
    }

    fun pressKeyboardLetter(i: Int): Boolean {
        //val hasBeenPressed = mutableStateOf(letters[i].isPressed.value)
        if (currentState.value == State.GUESS) {
            if (!keyboardLetters[i].isPressed.value) {
                keyboardLetters[i].pressLetter()

                //guessedLetters(letters[i])
                guessedLettersList.value += keyboardLetters[i].letter
                //gæt her
                guess(letter = keyboardLetters[i].letter)

                if(!isGameOver()){
                    currentState.value = State.SPIN
                }
                return true
            } else {
                return false
            }
        }
        return false
    }

    fun guess(letter: String) {
        var isGuessCorrect = false

        for (l in lettersInWord) {
            if (l.letter == letter) {
                isGuessCorrect = true
                l.makeVisible()
                //com.example.wheel.gameUI.ChangeLetterVisibility(letter = l)
                points.value += curSpinValue
            }
        }
        if (!isGuessCorrect) {
            looseALife()
        }
    }

    fun findCurrentWord() {
        val ran = (0..3).random()
        currentWord.value = allWords[1][ran]
        currentCategory.value = allWords[0][ran]
        //make letter objects
        for (letter in currentWord.value) {
            lettersInWord.add(Letter(letter.toString()))
        }
    }

    //returnerer spinvalue
    fun spinClicked() {
        if (currentState.value == State.SPIN) {
            val ran = (0..6).random()
            curSpinValue = values[ran]
            if (curSpinValue == 0) {
                spinValueString.value = "Bankrupt :("
                points.value = 0
            } else {
                spinValueString.value = "You spun $curSpinValue p!"
                currentState.value = State.GUESS
            }
        }
    }

    fun printLetter(letter: Letter): String {
        if (letter.isVisible.value) {
            return letter.letter
        } else {
            return ""
        }
    }

    fun isAllLettersGuessed(): Boolean {
        for (letter in lettersInWord) {
            if (!letter.isVisible.value) {
                return false
            }
        }
        return true
    }

    fun isGameOver(): Boolean {
        if (isAllLettersGuessed()) {
            currentState.value = State.WIN
            endMessage.value = winnerMessage
            return true
        }
        if (lives.value == 0) {
            currentState.value = State.LOST
            endMessage.value = loseMessage
            return true
        }
        return false
    }
}
