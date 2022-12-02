package com.example.wheel.model

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.compose.ui.graphics.Color

class KeyboardLetter constructor(val letter: String){
    private val _isPressed = mutableStateOf(false)
    var isPressed: State<Boolean> = _isPressed

    fun pressLetter(){
        _isPressed.value = true
    }

}