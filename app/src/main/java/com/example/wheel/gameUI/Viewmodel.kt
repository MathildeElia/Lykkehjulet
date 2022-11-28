package com.example.wheel.gameUI

import androidx.compose.material.Text
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import com.example.wheel.Letter

class Viewmodel : ViewModel() {

    //stateflow
    val values = arrayOf(0, 500, 600, 700, 800, 900, 10000)
    val spinValue = mutableStateOf("")
    val points = mutableStateOf(0)
    val currentWord = mutableStateOf("")
    val allWords: Array<Array<String>> = arrayOf(
        arrayOf("Around the House", "Rhyme Time", "On The Map", "Occupation"),
        arrayOf("DINING ROOM TABLE", "YUMMY IN MY TUMMY", "QUEBEC", "AUTO MECHANIC")
    )
    val letters = arrayOf(
        "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L",
        "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V", "W", "X", "Y", "Z"
    )
    val guessedLettersList = mutableStateOf("")
    val lettersInWord = mutableListOf<Letter>()

    fun pressKeyboardLetter(i: Int): Boolean {
        val hasBeenPressed = mutableStateOf(false)

        if (!hasBeenPressed.value) {
            hasBeenPressed.value = true

            //guessedLetters(letters[i])
            //guess here
            guessedLettersList.value += letters[i]
            //gæt her
            guessedLetters(letter = letters[i])
            return true
        } else {
            return false
        }
    }

    fun guessedLetters(letter: String): Int {
        var counter = 0

        for (l in lettersInWord) {
            if (l.letter == letter) {
                l.makeVisible()
                //ChangeLetterVisibility(letter = l)
                counter++
            }
        }
        return counter
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
        val ran = (0..6).random()
        val newSpinValue = values[ran]
        if (newSpinValue == 0) {
            spinValue.value = "Bankrupt!"
            points.value = 0
        } else {
            spinValue.value = "You got $newSpinValue points!"
            //det her skal ikke være her, men når der bliver gættet.
            points.value += newSpinValue
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
