package com.example.wheel.gameUI

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wheel.KeyboardLetter
import com.example.wheel.Letter
import com.example.wheel.State

class Viewmodel : ViewModel() {

    //stateflow
    val lives = mutableStateOf(5)
    val values = arrayOf(0, 500, 600, 700, 800, 900, 10000)
    var curSpinValue = 0
    val spinValueString = mutableStateOf("")
    val points = mutableStateOf(0)
    val currentWord = mutableStateOf("")
    val allWords: Array<Array<String>> = arrayOf(
        arrayOf("Around the House", "Rhyme Time", "On The Map", "Occupation"),
        arrayOf("DINING ROOM TABLE", "YUMMY IN MY TUMMY", "QUEBEC", "AUTO MECHANIC")
    )
    var keyboardLetters = ArrayList<KeyboardLetter>()


    val currentState = mutableStateOf(State.SPIN)

    val guessedLettersList = mutableStateOf("")
    val lettersInWord = mutableListOf<Letter>()

    fun looseALife(){
        lives.value--
    }

    fun addValuesToKeyboard(){
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

                //Tjek om spillet er færdigt!
                currentState.value = State.SPIN
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
        if(!isGuessCorrect){
            looseALife()
        }
    }

    fun findCurrentWord(): String {
        val ran = (0..3).random()
        currentWord.value = allWords[1][ran]

        //make letter objects
        for (letter in currentWord.value) {
            lettersInWord.add(Letter(letter.toString()))
        }
        return allWords[0][ran]
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
                //det her skal ikke være her, men når der bliver gættet.
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
}
