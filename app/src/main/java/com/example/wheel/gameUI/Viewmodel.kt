package com.example.wheel.gameUI

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wheel.Letter

class Viewmodel : ViewModel() {


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

    fun pressKeyboardLetter(i : Int){
        val hasBeenPressed =  mutableStateOf(false)

        if(!hasBeenPressed.value){
            hasBeenPressed.value = true

            //guessedLetters(letters[i])
            //guess here
            guessedLettersList.value += letters[i]
        }

        if (hasBeenPressed.value){
            //gæt her
            guessedLetters(letter = letters[i])
        }
    }

    fun guessedLetters(letter: String): Int {
        var counter = 0

        for (l in com.example.wheel.lettersInWord){
            if(l.letter == letter){
                l.makeVisible()
                //ChangeLetterVisibility(letter = l)
                counter++
            }
        }
        return counter
    }

}
