package com.example.wheel

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class KeyboardLetter constructor(val letter: String){
    private val _isPressed = mutableStateOf(false)
    var isPressed: State<Boolean> = _isPressed

    val color = mutableStateOf(Color.Magenta)


    fun pressLetter(){
        _isPressed.value = true
    }

}